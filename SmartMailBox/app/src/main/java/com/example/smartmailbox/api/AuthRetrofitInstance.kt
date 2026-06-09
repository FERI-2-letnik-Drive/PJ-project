package com.example.smartmailbox.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRetrofitInstance {
    // needs changing when it goes on different network
    private const val BASE_URL = "http://164.8.162.79:3001/"

    // Persistent cookie jar keeps the session cookie across app restarts.
    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SessionManager.prefs())
    }

    // retrofit sets the correct header automatically
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AuthAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }

    // RAIN mailbox endpoints, sharing the same session cookie.
    val mailboxApi: RainMailboxApi by lazy {
        retrofit.create(RainMailboxApi::class.java)
    }
}