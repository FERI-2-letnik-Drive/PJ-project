package com.example.smartmailbox.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SessionStateTest {

    @Test
    fun defaultState_isCheckingAndNotAuthenticated() {
        val state = SessionState()

        assertTrue(state.isChecking)
        assertFalse(state.isAuthenticated)
    }

    @Test
    fun copy_updatesFlags() {
        val state = SessionState().copy(isChecking = false, isAuthenticated = true)

        assertFalse(state.isChecking)
        assertTrue(state.isAuthenticated)
    }
}
