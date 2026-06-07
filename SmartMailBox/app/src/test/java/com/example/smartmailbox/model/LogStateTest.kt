package com.example.smartmailbox.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LogStateTest {

    @Test
    fun defaultState_isEmptyAndNotLoading() {
        val state = LogState()

        assertTrue(state.logs.isEmpty())
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
    }
}
