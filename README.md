# ITCS 6166: Computer Comm & Networks - Project
### Group Number: 3

#### Group Members:
- Jared Tamulynas
- Akhil Pravardhan Manneni
- Rakesh Anumula
- Sai Rohit Madapathi

## Project Overview
This project aims to investigate the role of AI, specifically generative AI and reinforcement learning, in enhancing networking performance. The focus is on using minimal network bandwidth for high-quality video streaming services. Please see section 3 for execution instructions for running our applications.

## Project Deliverables

### 1. Selected Paper/Topic
**Title:** BoB: Bandwidth Prediction for Real-Time Communications Using Heuristic and Reinforcement Learning  
**Repository:** [BoB GitHub Repository](https://github.com/NUStreaming/BoB)

------------------------------------------------------------------------------------------------

### 2. Inference Codes Execution / Replicated Results
**Objective:** Replicate results using the inference codes from the selected paper.  
**Minimum Requirement:** Successful execution of test/inference codes.  
**Demonstration Video:** [Test/Inference Code Execution](https://youtu.be/fXW81C41r4k)

![YouTube Video](https://img.youtube.com/vi/fXW81C41r4k/0.jpg)

#### Execution Instructions:
1. Clone the BoB repository from GitHub. [BoB GitHub Repository](https://github.com/NUStreaming/BoB)
2. Install the required dependencies.
3. Navigate to the environment folder.
4. Run 'make all' command in terminal.
5. Run the 'run_with_tc' script by entering ./run_with_tc.sh in Terminal.
6. Review the output for consistency with the results reported in the paper.

------------------------------------------------------------------------------------------------

### 3. Video Streaming Application
**Objective:** Build a video streaming application with sender and receiver, using the WebRTC real-time networking protocol.

**Overview:** Our project includes a mobile (Folder: Android Mobile App) and desktop application (Folder: Desktop App). Please see below specific execution instructions for each application.

**Demonstration Video:** [Android App Execution](https://youtu.be/uv9iXKcMmMo)

![YouTube Video](https://img.youtube.com/vi/fXW81C41r4k/0.jpg)

#### Android Application Execution Instructions:
- **Setup Instructions**:
  1. Clone the project repository
  3. Ensure Android Studio is installed with the required SDKs and emulators.
  4. Ensure IntelliJ IDEA is installed.
  5. Open the server project found in the 'Video-Streaming-App-backend' folder using IntelliJ IDEA.
  6. Find the 'Application.kt' file and run using the green run button (shown below).
  <img width="835" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/e8aaa0c6-0871-4148-b1e8-ff1834bf4e7e">
  8. Open the android project found in the 'Video-Streaming-App' folder using Android Studio.
  9. Add your local IP address in the `local.properties` file as `SIGNALING_SERVER_IP_ADDRESS`.
  <img width="1089" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/9ef85263-5fc4-40d6-8163-112fc473e939">
  11. Build and run the application on your Android device or emulator. (Need two devices to test, or one emulator and one real android device)
  <img width="1283" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/2d74b4a7-63dc-4125-b028-1a8c777e08eb">

------------------------------------------------------------------------------------------------

**Demonstration Video:** [Desktop App Execution](https://youtu.be/uv9iXKcMmMo)

![YouTube Video](https://img.youtube.com/vi/fXW81C41r4k/0.jpg)

#### Desktop Application Execution Instructions:
- **Setup Instructions**:
  1. Clone the project repository
  3. Ensure Android Studio is installed with the required SDKs and emulators.
  4. Ensure IntelliJ IDEA is installed.
  5. Open the server project found in the 'Video-Streaming-App-backend' folder using IntelliJ IDEA.
  6. Find the 'Application.kt' file and run using the green run button (shown below).
  <img width="835" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/e8aaa0c6-0871-4148-b1e8-ff1834bf4e7e">
  8. Open the android project found in the 'Video-Streaming-App' folder using Android Studio.
  9. Add your local IP address in the `local.properties` file as `SIGNALING_SERVER_IP_ADDRESS`.
  <img width="1089" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/9ef85263-5fc4-40d6-8163-112fc473e939">
  11. Build and run the application on your Android device or emulator. (Need two devices to test, or one emulator and one real android device)
  <img width="1283" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/2d74b4a7-63dc-4125-b028-1a8c777e08eb">

### References and Resources
**Description:** See below references used in building our applications.
- **WebRTC GitHub Repo**: [GetStream/webrtc-android](https://github.com/GetStream/webrtc-android)
- **Article Reference**: "Building a Video Chat App: WebRTC in Jetpack Compose" [Reference](https://getstream.io/blog/webrtc-on-android/)
