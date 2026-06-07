package com.example.smartmailbox.model

import com.example.smartmailbox.api.OpenLogResponse

data class LogState(
    val logs: List<OpenLogResponse> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
