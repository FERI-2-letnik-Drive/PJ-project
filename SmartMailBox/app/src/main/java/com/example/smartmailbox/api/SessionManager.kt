package com.example.smartmailbox.api

import android.content.Context
import android.content.SharedPreferences

/**
 * Holds the SharedPreferences used to persist the login session (cookies)
 * across app restarts. Initialized once from the Application class.
 */
object SessionManager {
    private const val PREFS_NAME = "smartmailbox_session"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.applicationContext
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun prefs(): SharedPreferences = prefs

    fun isInitialized(): Boolean = ::prefs.isInitialized
}
