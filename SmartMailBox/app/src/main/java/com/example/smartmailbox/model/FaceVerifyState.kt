package com.example.smartmailbox.model

data class FaceVerifyState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isVerified: Boolean = false,
    val isCameraStarted: Boolean = false
)