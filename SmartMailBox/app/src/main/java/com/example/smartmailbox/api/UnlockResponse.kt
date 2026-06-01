package com.example.smartmailbox.api

/**
 * Result of POST /mailboxes/{id}/unlock on the RAIN backend.
 * A successful (200) response means the user is allowed to open the mailbox.
 */
data class UnlockResponse(
    val message: String? = null,
    val weightKg: Double? = null,
    val openedAt: String? = null,
    val method: String? = null
)
