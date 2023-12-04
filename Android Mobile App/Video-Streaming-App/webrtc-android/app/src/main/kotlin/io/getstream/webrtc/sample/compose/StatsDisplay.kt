package io.getstream.webrtc.sample.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import org.webrtc.RTCStatsReport

@Composable
fun StatsDisplay(statsFlow: StateFlow<RTCStatsReport?>) {
    val stats by statsFlow.collectAsState(initial = null)

    val statsString = stats?.statsMap.toString()

    // Regular expressions to match the specific patterns
    val bitrateRegex = "availableOutgoingBitrate: (\\d+\\.\\d+)".toRegex()
    val packetLossRegex = "packetsLost: (\\d+)".toRegex()
    val rttRegex = "roundTripTime: (\\d+\\.\\d+)".toRegex()
    val bytesSentRegex = "bytesSent: (\\d+)".toRegex()
    val bytesReceivedRegex = "bytesReceived: (\\d+)".toRegex()
    val packetsSentRegex = "packetsSent: (\\d+)".toRegex()
    val packetsReceivedRegex = "packetsReceived: (\\d+)".toRegex()
    val jitterRegex = "jitter: (\\d+\\.\\d+)".toRegex()
    val frameRateRegex = "framesPerSecond: (\\d+\\.\\d+)".toRegex()
    val resolutionRegex = "frameWidth: (\\d+), frameHeight: (\\d+)".toRegex()

    // Extracting values
    val bitrateMatch = bitrateRegex.find(statsString)
    val packetLossMatch = packetLossRegex.find(statsString)
    val rttMatch = rttRegex.find(statsString)
    val bytesSentMatch = bytesSentRegex.find(statsString)
    val bytesReceivedMatch = bytesReceivedRegex.find(statsString)
    val packetsSentMatch = packetsSentRegex.find(statsString)
    val packetsReceivedMatch = packetsReceivedRegex.find(statsString)
    val jitterMatch = jitterRegex.find(statsString)
    val frameRateMatch = frameRateRegex.find(statsString)
    val resolutionMatch = resolutionRegex.find(statsString)

    Column(
        modifier = Modifier
            .height(300.dp)
            .padding(16.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))

    ) {
        Text(text = "WebRTC Stats:", fontWeight = FontWeight.Bold)

        Text(text = "Outgoing Bitrate: ${bitrateMatch?.groups?.get(1)?.value ?: "N/A"} kbps")
        Text(text = "Packet Loss: ${packetLossMatch?.groups?.get(1)?.value ?: "N/A"} packets")
        Text(text = "Round Trip Time: ${rttMatch?.groups?.get(1)?.value ?: "N/A"} ms")
        Text(text = "Bytes Sent: ${bytesSentMatch?.groups?.get(1)?.value ?: "N/A"} bytes")
        Text(text = "Bytes Received: ${bytesReceivedMatch?.groups?.get(1)?.value ?: "N/A"} bytes")
        Text(text = "Packets Sent: ${packetsSentMatch?.groups?.get(1)?.value ?: "N/A"}")
        Text(text = "Packets Received: ${packetsReceivedMatch?.groups?.get(1)?.value ?: "N/A"}")
        Text(text = "Jitter: ${jitterMatch?.groups?.get(1)?.value ?: "N/A"} ms")
        Text(text = "Frame Rate: ${frameRateMatch?.groups?.get(1)?.value ?: "N/A"} fps")
        Text(text = "Resolution: ${resolutionMatch?.groups?.get(1)?.value ?: "N/A"}x${resolutionMatch?.groups?.get(2)?.value ?: "N/A"}")
    }
}
