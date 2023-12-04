package io.getstream.webrtc.sample.compose

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.getstream.webrtc.sample.compose.ui.screens.stage.StageScreen
import io.getstream.webrtc.sample.compose.ui.screens.video.VideoCallScreen
import io.getstream.webrtc.sample.compose.ui.theme.WebRTCTheme
import io.getstream.webrtc.sample.compose.webrtc.SignalingClient
import io.getstream.webrtc.sample.compose.webrtc.peer.StreamPeerConnectionFactory
import io.getstream.webrtc.sample.compose.webrtc.sessions.*

/**
 * MainActivity is the entry point of the application. It extends ComponentActivity
 * which provides the base class for activities using Jetpack Compose.
 */
class MainActivity : ComponentActivity() {

  /**
   * The onCreate method is called when the activity is starting.
   * This is where you initialize your activity.
   */
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Requesting permissions necessary for WebRTC video and audio streaming.
    requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), 0)

    // Setup of the WebRTC session manager.
    val sessionManager: WebRtcSessionManager = WebRtcSessionManagerImpl(
      context = this,
      signalingClient = SignalingClient(),
      peerConnectionFactory = StreamPeerConnectionFactory(this)
    )

    // Set the content of the activity using Jetpack Compose.
    setContent {
      // Apply the app theme from WebrtcSampleComposeTheme.
      WebRTCTheme {
        // Providing the session manager to the composable tree.
        CompositionLocalProvider(LocalWebRtcSessionManager provides sessionManager) {
          // Defining a surface container with the background color from the theme.
          Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
          ) {
            // State handling to toggle between stage screen and video call screen.
            var onCallScreen by remember { mutableStateOf(false) }
            // Observing the signaling client's session state.
            val state by sessionManager.signalingClient.sessionStateFlow.collectAsState()

            val statsFlow = sessionManager.getStatsFlow()


            // Display the stage screen if not in a call, otherwise show the video call screen.
            if (!onCallScreen) {
              StageScreen(state = state) { onCallScreen = true }
            } else {
              Box(
                contentAlignment = Alignment.TopStart
              ) {
                VideoCallScreen()

                StatsDisplay(statsFlow = statsFlow)
              }
            }
          }
        }
      }
    }
  }
}
