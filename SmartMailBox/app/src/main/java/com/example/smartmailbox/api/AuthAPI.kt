package com.example.smartmailbox.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface AuthAPI {
    @POST("users/mobile-login")
    suspend fun postMobileLogin(@Body request: MobileLoginRequest): Response<MobileLoginResponse>

    @GET("users/profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @GET("users/logout")
    suspend fun logout(): Response<Unit>

    @PUT("users/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<ProfileResponse>

    @PUT("users/password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<Unit>

    @POST("users/register")
    suspend fun postRegister(@Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @Multipart
    @POST("users/mobile-login/face-verify")
    suspend fun postFaceVerify(@Part currentImage: MultipartBody.Part
    ): Response<FaceVerifyResponse>

    @Multipart
    @POST("users/2fa/enable")
    suspend fun enableTwoFactor(@Part image: MultipartBody.Part): Response<TwoFactorResponse>
}