# ITCS 6166: Computer Comm & Networks - Project
### Group Number: 3

#### Group Members:
- Jared Tamulynas
- Akhil Pravardhan Manneni
- Rakesh Anumula
- Sai Rohit Madapathi

## Project Overview
This project aims to investigate the role of AI, specifically generative AI and reinforcement learning, in enhancing networking performance. The focus is on using minimal network bandwidth for high-quality video streaming services.

## Project Deliverables

### 1. Selected Paper
**Title:** BoB: Bandwidth Prediction for Real-Time Communications Using Heuristic and Reinforcement Learning  
**Repository:** [BoB GitHub Repository](https://github.com/NUStreaming/BoB)

### 2. Code Replication
**Objective:** Replicate results using the code from the selected paper.  
**Minimum Requirement:** Successful execution of test/inference codes.  
**Demonstration Video:** [Test/Inference Code Execution](https://youtu.be/uv9iXKcMmMo)

#### Execution Instructions:
1. Clone the BoB repository from GitHub. [BoB GitHub Repository](https://github.com/NUStreaming/BoB)
2. Install any required dependencies.
3. Navigate to the environment folder.
4. run 'make all'
5. Run the test/inference script using ./run_with_tc.sh
6. Review the output for consistency with the results reported in the paper.

### 3. Video Streaming Application
**Objective:** Build a video streaming application with sender and receiver, using the WebRTC real-time networking protocol.

#### Key Components:
- **WebRTC for Android**: Use of the WebRTC protocol for real-time audio and video streaming. The protocol allows peer-to-peer communication, reducing latency and server load.
- **Signaling Server**: Implementation of a WebRTC signaling server to manage peer connections.
- **Session Description Protocol (SDP)**: Utilization of SDP for describing peer connection details.
- **Interactive Connectivity Establishment (ICE)**: ICE protocol for establishing peer connections across NAT/firewalls.
- **PeerConnection and PeerConnectionFactory**: Core WebRTC components for establishing and managing peer connections.
- **Signaling Client**: Handling exchange of SDP offers/answers and ICE candidates through a signaling server.
- **WebRtcSessionManager**: Manages the peer connection and signaling responses.

#### Execution Instructions:

- **Setup Instructions**:
  1. Clone our project repository
  2. Ensure Android Studio is set up with the necessary SDKs and emulators.
  3. Ensure ItelliJ IDEA is set up.
  4. Open the server project in ItelliJ IDEA.
  5. Run the application file.
  6. Open the android project in Android Studio and sync Gradle.
  7. Add your local IP address in the `local.properties` file as `SIGNALING_SERVER_IP_ADDRESS`.
  8. Build and run the application on your Android device or emulator. (Need two devices to test)

### References and Resources
- **WebRTC GitHub Repo**: [GetStream/webrtc-android](https://github.com/GetStream/webrtc-android)
- **Article Reference**: "Building a Video Chat App: WebRTC in Jetpack Compose" [Reference](https://getstream.io/blog/webrtc-on-android/)
