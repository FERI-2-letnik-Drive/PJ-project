package com.example.smartmailbox.model

data class HomeState(
    val twoFactorEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null,
    val errorMessage: String? = null
)
