package com.example.smartmailbox.model

data class ProfileState(
    val username: String = "",
    val email: String = "",
    val twoFactorEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)