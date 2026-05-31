package com.example.smartmailbox.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmailbox.api.AuthRetrofitInstance
import com.example.smartmailbox.model.SessionState
import kotlinx.coroutines.launch

/**
 * Decides whether a stored session is still valid on app start (auto-login)
 * and clears the session on logout.
 */
class SessionViewModel : ViewModel() {

    var sessionState by mutableStateOf(SessionState())
        private set

    /** Validates the persisted session by hitting the profile endpoint. */
    fun checkSession() {
        viewModelScope.launch {
            sessionState = sessionState.copy(isChecking = true)
            val authenticated = try {
                AuthRetrofitInstance.api.getProfile().isSuccessful
            } catch (e: Exception) {
                false
            }
            sessionState = SessionState(
                isChecking = false,
                isAuthenticated = authenticated
            )
        }
    }

    /** Logs out on the backend and clears the locally stored session cookie. */
    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                AuthRetrofitInstance.api.logout()
            } catch (e: Exception) {
                // Ignore network errors: we clear the local session regardless.
            } finally {
                AuthRetrofitInstance.cookieJar.clear()
                sessionState = SessionState(isChecking = false, isAuthenticated = false)
                onComplete()
            }
        }
    }
}
