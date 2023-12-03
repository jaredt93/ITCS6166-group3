import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*

/**
 * Singleton object to manage WebRTC sessions.
 * It handles the creation, state management, and messaging of WebRTC sessions.
 */
object SessionManager {

    // Coroutine scope for asynchronous tasks within the session manager.
    private val sessionManagerScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // Mutex for thread-safe operations on shared resources.
    private val mutex = Mutex()

    // A map to keep track of connected clients using their session IDs.
    private val clients = mutableMapOf<UUID, DefaultWebSocketServerSession>()

    // The current state of the WebRTC session.
    private var sessionState: WebRTCSessionState = WebRTCSessionState.Impossible

    /**
     * Called when a new session is started.
     * Registers the session and updates the session state accordingly.
     *
     * @param sessionId The unique identifier of the session.
     * @param session The WebSocket session corresponding to the client.
     */
    fun onSessionStarted(sessionId: UUID, session: DefaultWebSocketServerSession) {
        sessionManagerScope.launch {
            mutex.withLock {
                // Limit the number of peers to two.
                if (clients.size > 1) {
                    sessionManagerScope.launch(NonCancellable) {
                        session.send(Frame.Close()) // Close if more than two peers are present.
                    }
                    return@launch
                }
                clients[sessionId] = session // Add the new session.
                session.send("Added as a client: $sessionId")
                // Update the session state to ready if two clients are connected.
                if (clients.size > 1) {
                    sessionState = WebRTCSessionState.Ready
                }
                notifyAboutStateUpdate() // Notify all clients about the updated state.
            }
        }
    }

    /**
     * Handles incoming messages from a session.
     * Distributes different types of messages (STATE, OFFER, ANSWER, ICE) to their respective handlers.
     *
     * @param sessionId The session ID from which the message is received.
     * @param message The received message.
     */
    fun onMessage(sessionId: UUID, message: String) {
        when {
            message.startsWith(MessageType.STATE.toString(), true) -> handleState(sessionId)
            message.startsWith(MessageType.OFFER.toString(), true) -> handleOffer(sessionId, message)
            message.startsWith(MessageType.ANSWER.toString(), true) -> handleAnswer(sessionId, message)
            message.startsWith(MessageType.ICE.toString(), true) -> handleIce(sessionId, message)
        }
    }

    private fun handleState(sessionId: UUID) {
        sessionManagerScope.launch {
            clients[sessionId]?.send("${MessageType.STATE} $sessionState")
        }
    }

    private fun handleOffer(sessionId: UUID, message: String) {
        if (sessionState != WebRTCSessionState.Ready) {
            error("Session should be in Ready state to handle offer")
        }
        sessionState = WebRTCSessionState.Creating
        println("handling offer from $sessionId")
        notifyAboutStateUpdate()
        val clientToSendOffer = clients.filterKeys { it != sessionId }.values.first()
        clientToSendOffer.send(message)
    }

    private fun handleAnswer(sessionId: UUID, message: String) {
        if (sessionState != WebRTCSessionState.Creating) {
            error("Session should be in Creating state to handle answer")
        }
        println("handling answer from $sessionId")
        val clientToSendAnswer = clients.filterKeys { it != sessionId }.values.first()
        clientToSendAnswer.send(message)
        sessionState = WebRTCSessionState.Active
        notifyAboutStateUpdate()
    }

    private fun handleIce(sessionId: UUID, message: String) {
        println("handling ice from $sessionId")
        val clientToSendIce = clients.filterKeys { it != sessionId }.values.first()
        clientToSendIce.send(message)
    }

    /**
     * Called when a session is closed.
     * Removes the session from the list of active clients and updates the session state.
     *
     * @param sessionId The unique identifier of the session that is closing.
     */
    fun onSessionClose(sessionId: UUID) {
        sessionManagerScope.launch {
            mutex.withLock {
                clients.remove(sessionId) // Remove the closed session.
                sessionState = WebRTCSessionState.Impossible // Update the session state.
                notifyAboutStateUpdate() // Notify remaining clients about the state change.
            }
        }
    }

    // Enum defining various states of a WebRTC session.
    enum class WebRTCSessionState {
        Active, // Offer and Answer messages have been sent.
        Creating, // Creating session, offer has been sent.
        Ready, // Both clients available and ready to initiate session.
        Impossible // Less than two clients are connected.
    }

    // Enum for identifying types of messages in WebRTC signaling.
    enum class MessageType {
        STATE,
        OFFER,
        ANSWER,
        ICE
    }

    /**
     * Notifies all connected clients about the current state of the session.
     */
    private fun notifyAboutStateUpdate() {
        clients.forEach { (_, client) ->
            client.send("${MessageType.STATE} $sessionState")
        }
    }

    /**
     * Extension function to send a text message through a WebSocket session.
     *
     * @param message The message to be sent.
     */
    private fun DefaultWebSocketServerSession.send(message: String) {
        sessionManagerScope.launch {
            this@send.send(Frame.Text(message))
        }
    }
}