package com.example.smartmailbox.api

data class FaceVerifyResponse(
    val verified: Boolean? = null,
    val message: String? = null,
    val user: MobileLoginResponse? = null
)