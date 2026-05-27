package com.example.smartmailbox.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmailbox.api.AuthRetrofitInstance
import com.example.smartmailbox.api.RegisterRequest
import com.example.smartmailbox.model.RegisterState
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel : ViewModel() {

    var registerState by mutableStateOf(RegisterState())
        private set

    fun onUsernameChange(username: String) {
        registerState = registerState.copy(
            username = username,
            errorMessage = null
        )
    }

    fun onEmailChange(email: String) {
        registerState = registerState.copy(
            email = email,
            errorMessage = null
        )
    }

    fun onPasswordChange(password: String) {
        registerState = registerState.copy(
            password = password,
            errorMessage = null
        )
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        registerState = registerState.copy(
            confirmPassword = confirmPassword,
            errorMessage = null
        )
    }

    fun register() {
        val username = registerState.username.trim()
        val email = registerState.email.trim()
        val password = registerState.password
        val confirmPassword = registerState.confirmPassword

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            registerState = registerState.copy(
                errorMessage = "All fields are required"
            )
            return
        }

        if (password != confirmPassword) {
            registerState = registerState.copy(
                errorMessage = "Passwords do not match"
            )
            return
        }

        viewModelScope.launch {
            registerState = registerState.copy(
                isLoading = true,
                errorMessage = null,
                isRegistered = false
            )

            try {
                val response = AuthRetrofitInstance.api.postRegister(
                    RegisterRequest(
                        username = username,
                        email = email,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    registerState = registerState.copy(
                        isLoading = false,
                        isRegistered = true,
                        password = "",
                        confirmPassword = "",
                        errorMessage = null
                    )
                } else {
                    registerState = registerState.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(response.errorBody()?.string())
                    )
                }
            } catch (e: Exception) {
                registerState = registerState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun clearRegisterNavigationFlag() {
        registerState = registerState.copy(
            isRegistered = false
        )
    }

    private fun getErrorMessage(errorJson: String?): String {
        if (errorJson.isNullOrBlank()) {
            return "Registration failed"
        }

        return try {
            JSONObject(errorJson).optString("message", "Registration failed")
        } catch (e: Exception) {
            "Registration failed"
        }
    }
}