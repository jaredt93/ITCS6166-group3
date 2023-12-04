# ITCS 6166: Computer Comm & Networks - Project
### Group Number: 3

#### Group Members:
- Jared Tamulynas
- Akhil Pravardhan Manneni
- Rakesh Anumula
- Sai Rohit Madapathi

------------------------------------------------------------------------------------------------

### Video Streaming Application
**Objective:** Build a video streaming application with sender and receiver, using the WebRTC real-time networking protocol.

**Overview:** Our project includes a mobile (Folder: Android Mobile App) and desktop application (Folder: Desktop App). Please see below specific execution instructions for each application.

**Demonstration Video:** [Android App Execution](https://www.youtube.com/watch?v=fI6-y4lLvTM)

[![YouTube Video](https://img.youtube.com/vi/fI6-y4lLvTM/0.jpg)](https://www.youtube.com/watch?v=fI6-y4lLvTM)

#### Android Application Execution Instructions:
- **Setup Instructions**:
1. Clone the project repository
2. Ensure Android Studio is installed with the required SDKs and emulators.
3. Ensure IntelliJ IDEA is installed.
4. Open the server project found in the 'Video-Streaming-App-backend' folder using IntelliJ IDEA.
5. Find the 'Application.kt' file and run using the green run button (shown below),
6. Open the android project found in the 'Video-Streaming-App' folder using Android Studio.
7. Add your local IP address in the `local.properties` file as `SIGNALING_SERVER_IP_ADDRESS`.
8. Build and run the application on your Android device or emulator. (Need two devices to test, or one emulator and one real android device)

<img width="835" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/e8aaa0c6-0871-4148-b1e8-ff1834bf4e7e">

<img width="1089" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/9ef85263-5fc4-40d6-8163-112fc473e939">

<img width="1283" alt="image" src="https://github.com/jaredt93/ITCS6166-group3/assets/36479936/2d74b4a7-63dc-4125-b028-1a8c777e08eb">

------------------------------------------------------------------------------------------------
### References and Resources
**Description:** See below references used in building our applications.
- **WebRTC GitHub Repo**: [GetStream/webrtc-android](https://github.com/GetStream/webrtc-android)
- **Article Reference**: "Building a Video Chat App: WebRTC in Jetpack Compose" [Reference](https://getstream.io/blog/webrtc-on-android/)
