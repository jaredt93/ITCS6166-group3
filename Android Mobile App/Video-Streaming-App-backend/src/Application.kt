import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.time.Duration
import java.util.*

/**
 * Main entry point for the Ktor server.
 */
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

/**
 * Configures the Ktor application with modules, particularly for handling WebRTC signaling.
 *
 * @param testing Flag to indicate if the server is running in a testing environment.
 */
@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    // Installing the WebSockets feature with specific configuration.
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15) // Period for sending ping messages.
        timeout = Duration.ofSeconds(15) // Timeout duration for a connection.
        maxFrameSize = Long.MAX_VALUE // Maximum size for a frame payload.
        masking = false // Disabling masking of frames.
    }

    // Setting up the routing for the application.
    routing {

        // HTTP GET request handling at the root path.
        get("/") {
            // Responding with a simple text message.
            call.respond("Hello from WebRTC signaling server")
        }

        // WebSocket endpoint for WebRTC signaling at '/rtc'.
        webSocket("/rtc") {
            // Generate a unique session ID for each WebSocket connection.
            val sessionID = UUID.randomUUID()
            try {
                // Notify the session manager that a new session has started.
                SessionManager.onSessionStarted(sessionID, this)

                // Handling incoming frames from the WebSocket connection.
                for (frame in incoming) {
                    when (frame) {
                        // Handle text frames.
                        is Frame.Text -> {
                            // Pass the message to the session manager.
                            SessionManager.onMessage(sessionID, frame.readText())
                        }
                        // Other frame types are ignored.
                        else -> Unit
                    }
                }

                // Log when exiting the incoming loop and closing the session.
                println("Exiting incoming loop, closing session: $sessionID")
                SessionManager.onSessionClose(sessionID)
            } catch (e: ClosedReceiveChannelException) {
                // Handle exceptions when the receive channel is closed unexpectedly.
                println("onClose $sessionID")
                SessionManager.onSessionClose(sessionID)
            } catch (e: Throwable) {
                // Handle any other exceptions during the WebSocket communication.
                println("onError $sessionID $e")
                SessionManager.onSessionClose(sessionID)
            }
        }
    }
}
