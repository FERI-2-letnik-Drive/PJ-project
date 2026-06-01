package com.example.smartmailbox.api

/**
 * A single mailbox open event as returned by GET /mailboxes/{id}/logs.
 */
data class OpenLogResponse(
    val _id: String? = null,
    val method: String? = null,
    val weightKg: Double? = null,
    val openedAt: String? = null,
    val userId: OpenLogUser? = null
)

/** The user who opened the mailbox (populated by the backend). */
data class OpenLogUser(
    val username: String? = null,
    val email: String? = null
)
