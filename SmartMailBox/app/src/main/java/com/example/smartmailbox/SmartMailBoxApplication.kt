package com.example.smartmailbox

import android.app.Application
import com.example.smartmailbox.api.SessionManager

/**
 * Application entry point. Initializes the session storage early so the
 * persistent cookie jar is ready before any network call happens.
 */
class SmartMailBoxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.init(this)
    }
}
