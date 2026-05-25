package com.example.smartmailbox.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("users/mobile-login")
    suspend fun postMobileLogin(@Body request: MobileLoginRequest): Response<MobileLoginResponse>
}