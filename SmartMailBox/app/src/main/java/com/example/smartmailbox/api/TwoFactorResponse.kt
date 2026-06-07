package com.example.smartmailbox.api

/**
 * Response of POST /users/2fa/enable.
 */
data class TwoFactorResponse(
    val twoFactorEnabled: Boolean? = null,
    val message: String? = null
)
