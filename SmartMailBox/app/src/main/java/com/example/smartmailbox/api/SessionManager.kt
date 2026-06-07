package com.example.smartmailbox.api

import android.content.Context
import android.content.SharedPreferences

/**
 * Holds the SharedPreferences used to persist the login session (cookies)
 * across app restarts. Initialized once from the Application class.
 */
object SessionManager {
    private const val PREFS_NAME = "smartmailbox_session"

    private lateinit var sharedPrefs: SharedPreferences

    fun init(context: Context) {
        sharedPrefs = context.applicationContext
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun prefs(): SharedPreferences = sharedPrefs

    fun isInitialized(): Boolean = ::sharedPrefs.isInitialized
}
