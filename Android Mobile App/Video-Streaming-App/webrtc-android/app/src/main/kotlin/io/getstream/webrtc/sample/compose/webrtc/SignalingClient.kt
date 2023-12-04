package io.getstream.webrtc.sample.compose.webrtc

import io.getstream.log.taggedLogger
import io.getstream.webrtc.sample.compose.BuildConfig
import kotlinx.coroutines.*
import okhttp3.*
import kotlinx.coroutines.flow.*

/**
 * SignalingClient is responsible for managing the signaling process in the WebRTC application.
 * Signaling is a crucial part of establishing a WebRTC connection where two peers exchange
 * information about how they will communicate.
 */
class SignalingClient {
  // Logger for debugging and logging purposes.
  private val logger by taggedLogger("Call:SignalingClient")

  // Coroutine scope for managing asynchronous tasks.
  private val signalingScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

  // OkHttpClient instance for handling WebSocket connections.
  private val client = OkHttpClient()

  // Building the request to connect to the signaling server.
  private val request = Request
    .Builder()
    .url(BuildConfig.SIGNALING_SERVER_IP_ADDRESS)
    .build()

  // Establishing a WebSocket connection with the signaling server.
  private val ws = client.newWebSocket(request, SignalingWebSocketListener())

  // MutableStateFlow to keep track of the current session state.
  private val _sessionStateFlow = MutableStateFlow(WebRTCSessionState.Offline)
  val sessionStateFlow: StateFlow<WebRTCSessionState> = _sessionStateFlow

  // MutableSharedFlow to send signaling commands and their associated data.
  private val _signalingCommandFlow = MutableSharedFlow<Pair<SignalingCommand, String>>()
  val signalingCommandFlow: SharedFlow<Pair<SignalingCommand, String>> = _signalingCommandFlow

  /**
   * Sends a signaling command along with a message over the WebSocket connection.
   * @param signalingCommand The type of signaling command to send.
   * @param message The message associated with the signaling command.
   */
  fun sendCommand(signalingCommand: SignalingCommand, message: String) {
    logger.d { "[sendCommand] $signalingCommand $message" }
    ws.send("$signalingCommand $message")
  }

  /**
   * WebSocketListener class to handle WebSocket events, particularly incoming messages.
   */
  private inner class SignalingWebSocketListener : WebSocketListener() {
    override fun onMessage(webSocket: WebSocket, text: String) {
      // Handling different types of messages based on the signaling command.
      when {
        text.startsWith(SignalingCommand.STATE.toString(), true) ->
          handleStateMessage(text)
        text.startsWith(SignalingCommand.OFFER.toString(), true) ->
          handleSignalingCommand(SignalingCommand.OFFER, text)
        text.startsWith(SignalingCommand.ANSWER.toString(), true) ->
          handleSignalingCommand(SignalingCommand.ANSWER, text)
        text.startsWith(SignalingCommand.ICE.toString(), true) ->
          handleSignalingCommand(SignalingCommand.ICE, text)
      }
    }
  }

  /**
   * Handles a state message by updating the session state.
   * @param message The message containing the new state.
   */
  private fun handleStateMessage(message: String) {
    val state = getSeparatedMessage(message)
    _sessionStateFlow.value = WebRTCSessionState.valueOf(state)
  }

  /**
   * Handles a signaling command by emitting it to the signaling command flow.
   * @param command The signaling command received.
   * @param text The associated message.
   */
  private fun handleSignalingCommand(command: SignalingCommand, text: String) {
    val value = getSeparatedMessage(text)
    logger.d { "received signaling: $command $value" }
    signalingScope.launch {
      _signalingCommandFlow.emit(command to value)
    }
  }

  /**
   * Extracts the message content after the first space character.
   * @param text The complete message text.
   * @return The extracted message content.
   */
  private fun getSeparatedMessage(text: String) = text.substringAfter(' ')

  /**
   * Cleans up resources and closes the WebSocket connection.
   */
  fun dispose() {
    _sessionStateFlow.value = WebRTCSessionState.Offline
    signalingScope.cancel()
    ws.cancel()
  }
}

/**
 * Enum representing various states of the WebRTC session.
 */
enum class WebRTCSessionState {
  Active, // Offer and Answer messages have been sent
  Creating, // Creating session, offer has been sent
  Ready, // Both clients available and ready to initiate session
  Impossible, // We have less than two clients connected to the server
  Offline // Unable to connect to signaling server
}

/**
 * Enum representing signaling commands used in the WebRTC process.
 */
enum class SignalingCommand {
  STATE, // Command for WebRTCSessionState
  OFFER, // To send or receive an offer
  ANSWER, // To send or receive an answer
  ICE // To send and receive ICE candidates
}