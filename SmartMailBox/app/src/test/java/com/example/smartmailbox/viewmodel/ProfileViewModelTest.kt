package com.example.smartmailbox.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test

class ProfileViewModelTest {

    @Test
    fun initialState_isEmpty() {
        val viewModel = ProfileViewModel()

        assertEquals("", viewModel.profileState.username)
        assertEquals("", viewModel.profileState.email)
        assertFalse(viewModel.profileState.isLoading)
    }

    @Test
    fun clearMessages_clearsErrorAndSuccess() {
        val viewModel = ProfileViewModel()

        viewModel.clearMessages()

        assertNull(viewModel.profileState.errorMessage)
        assertNull(viewModel.profileState.successMessage)
    }
}
