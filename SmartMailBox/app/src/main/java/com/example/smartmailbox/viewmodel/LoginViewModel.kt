package com.example.smartmailbox.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmailbox.api.AuthRetrofitInstance
import com.example.smartmailbox.api.MobileLoginRequest
import com.example.smartmailbox.model.LoginState
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel : ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

    fun onUsernameChange(username: String) {
        loginState = loginState.copy(
            username = username,
            errorMessage = null
        )
    }

    fun onPasswordChange(password: String) {
        loginState = loginState.copy(
            password = password,
            errorMessage = null
        )
    }

    fun login() {
        val username = loginState.username.trim()
        val password = loginState.password

        if (username.isEmpty() || password.isEmpty()) {
            loginState = loginState.copy(
                errorMessage = "Username and password are required"
            )
            return
        }

        viewModelScope.launch {
            loginState = loginState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val response = AuthRetrofitInstance.api.postMobileLogin(
                    MobileLoginRequest(
                        username = username,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.twoFactorRequired == true) {
                        loginState = loginState.copy(
                            isLoading = false,
                            twoFactorRequired = true,
                            isLoggedIn = false,
                            password = ""
                        )
                    } else {
                        loginState = loginState.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            twoFactorRequired = false,
                            password = ""
                        )
                    }
                } else {
                    val errorJson = response.errorBody()?.string()

                    val errorMessage = getErrorMessage(errorJson)

                    loginState = loginState.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            } catch (e: Exception) {
                loginState = loginState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Something went wrong"
                )
            }
        }
    }

    private fun getErrorMessage(errorJson: String?): String {
        if (errorJson.isNullOrBlank()) {
            return "Login failed"
        }

        return try {
            // parsing works -> message field, else "Login failed"
            JSONObject(errorJson).optString("message", "Login failed")
        } catch (e: Exception) {
            "Login failed"
        }
    }

    fun clearLoginNavigationFlags() {
        loginState = loginState.copy(
            isLoggedIn = false,
            twoFactorRequired = false
        )
    }
}