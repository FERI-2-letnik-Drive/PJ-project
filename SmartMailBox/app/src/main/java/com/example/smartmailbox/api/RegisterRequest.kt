package com.example.smartmailbox.api

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)