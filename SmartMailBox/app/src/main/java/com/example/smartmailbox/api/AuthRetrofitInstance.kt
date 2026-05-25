package com.example.smartmailbox.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRetrofitInstance {
    // needs changing when it goes on different network
    private const val BASE_URL = "http://192.168.175.1:3001/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()

            chain.proceed(request)
        }
        .build()

    val api: AuthAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthAPI::class.java)
    }
}