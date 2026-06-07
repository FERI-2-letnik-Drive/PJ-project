package com.example.smartmailbox.api

import org.junit.Assert.assertEquals
import org.junit.Test

class RequestModelsTest {

    @Test
    fun updateProfileRequest_holdsValues() {
        val request = UpdateProfileRequest(username = "alice", email = "alice@example.com")

        assertEquals("alice", request.username)
        assertEquals("alice@example.com", request.email)
    }

    @Test
    fun changePasswordRequest_holdsValues() {
        val request = ChangePasswordRequest(
            currentPassword = "old",
            newPassword = "new",
            confirmPassword = "new"
        )

        assertEquals("old", request.currentPassword)
        assertEquals("new", request.newPassword)
        assertEquals("new", request.confirmPassword)
    }
}
