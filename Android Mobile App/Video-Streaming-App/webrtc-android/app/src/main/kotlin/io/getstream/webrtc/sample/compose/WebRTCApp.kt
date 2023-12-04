package io.getstream.webrtc.sample.compose

import android.app.Application
import io.getstream.log.android.AndroidStreamLogger

/**
 * This is the main application class for our WebRTC-based project.
 * It extends from Android's Application class and sets up the necessary configurations
 * for our application on creation.
 *
 * WebRTC (Web Real-Time Communication) is a technology that enables real-time communication
 * such as video and audio streaming directly in the web browser and mobile platforms without
 * the need for installing additional plugins or applications.
 *
 * In this class, we are also initializing the AndroidStreamLogger, a utility from the Stream
 * library which helps in logging debug information. This is particularly useful for our
 * application as it heavily relies on real-time data communication and we need to keep track
 * of events and errors efficiently.
 */
class WebRTCApp : Application() {

  /**
   * The onCreate() method is called when the application is starting, before any
   * activity, service, or receiver objects (excluding content providers) have been created.
   * We are using this method to set up our logging mechanism.
   */
  override fun onCreate() {
    super.onCreate()

    // AndroidStreamLogger.installOnDebuggableApp() is a utility function from the Stream library.
    // It sets up a logger that only works on debuggable builds. This is helpful to avoid
    // exposing log information in production builds. The 'this' keyword refers to the
    // instance of the Application class, which is needed to initialize the logger.
    AndroidStreamLogger.installOnDebuggableApp(this)
  }
}
