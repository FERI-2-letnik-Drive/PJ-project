package com.example.smartmailbox.api

/**
 * A mailbox as returned by the RAIN backend (GET /mailboxes).
 */
data class MailboxResponse(
    val _id: String? = null,
    val label: String? = null,
    val location: String? = null,
    val isLocked: Boolean? = null,
    val weightKg: Double? = null
)
