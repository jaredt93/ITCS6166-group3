package io.getstream.webrtc.sample.compose.ui.screens.stage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.getstream.webrtc.sample.compose.R
import io.getstream.webrtc.sample.compose.webrtc.WebRTCSessionState

/**
 * Composable function to display the stage screen of the WebRTC application.
 *
 * This screen provides the user interface before joining a WebRTC call. It reflects
 * the current state of the WebRTC session and allows the user to join a call based on the session's readiness.
 *
 * @param state The current state of the WebRTC session.
 * @param onJoinCall A callback function to invoke when the user presses the join call button.
 */
@Composable
fun StageScreen(
  state: WebRTCSessionState,
  onJoinCall: () -> Unit
) {
  // A box layout to center the content in the screen.
  Box(modifier = Modifier.fillMaxSize()) {
    // State to enable or disable the call button based on the WebRTC session state.
    var enabledCall by remember { mutableStateOf(false) }

    // Determine the text to be displayed and the enabled state of the button based on the current WebRTC session state.
    val text = when (state) {
      WebRTCSessionState.Offline -> {
        enabledCall = false
        stringResource(id = R.string.session_offline)
      }
      WebRTCSessionState.Impossible -> {
        enabledCall = false
        stringResource(id = R.string.session_impossible)
      }
      WebRTCSessionState.Ready -> {
        enabledCall = true
        stringResource(id = R.string.button_start_session)
      }
      WebRTCSessionState.Creating -> {
        enabledCall = true
        stringResource(id = R.string.button_join_session)
      }
      WebRTCSessionState.Active -> {
        enabledCall = false
        stringResource(id = R.string.session_active)
      }
    }

    // Button composable for initiating the action to join the call.
    // The button is enabled or disabled based on the session state.
    Button(
      modifier = Modifier.align(Alignment.Center),
      enabled = enabledCall,
      onClick = { onJoinCall.invoke() }
    ) {
      // Text displayed on the button, dynamically changes based on the session state.
      Text(
        text = text,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold
      )
    }
  }
}
