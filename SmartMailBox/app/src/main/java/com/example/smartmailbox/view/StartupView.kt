package com.example.smartmailbox.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.smartmailbox.viewmodel.SessionViewModel

/**
 * Splash screen shown while we check for a stored session. Routes the user
 * straight to Home if already logged in, otherwise to Login.
 */
@Composable
fun StartupView(
    sessionViewModel: SessionViewModel,
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit
) {
    val state = sessionViewModel.sessionState

    LaunchedEffect(Unit) {
        sessionViewModel.checkSession()
    }

    LaunchedEffect(state.isChecking) {
        if (!state.isChecking) {
            if (state.isAuthenticated) onAuthenticated() else onUnauthenticated()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
