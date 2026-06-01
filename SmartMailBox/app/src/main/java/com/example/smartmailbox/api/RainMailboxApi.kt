package com.example.smartmailbox.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * RAIN backend endpoints for the user's mailboxes. Uses the session cookie
 * (same client as AuthAPI) to identify the logged-in user.
 */
interface RainMailboxApi {

    @GET("mailboxes")
    suspend fun getMailboxes(): Response<List<MailboxResponse>>

    @POST("mailboxes/{id}/unlock")
    suspend fun unlock(@Path("id") mailboxId: String): Response<UnlockResponse>
}
