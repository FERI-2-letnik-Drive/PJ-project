package com.example.smartmailbox.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmailbox.api.AuthRetrofitInstance
import com.example.smartmailbox.api.ChangePasswordRequest
import com.example.smartmailbox.api.UpdateProfileRequest
import com.example.smartmailbox.model.ProfileState
import kotlinx.coroutines.launch
import org.json.JSONObject


class ProfileViewModel : ViewModel() {

    var profileState by mutableStateOf(ProfileState())
        private set

    /** Loads the logged-in user's profile from the backend. */
    fun loadProfile() {
        viewModelScope.launch {
            profileState = profileState.copy(isLoading = true, errorMessage = null)
            try {
                val response = AuthRetrofitInstance.api.getProfile()
                if (response.isSuccessful) {
                    val body = response.body()
                    profileState = profileState.copy(
                        username = body?.username ?: "",
                        email = body?.email ?: "",
                        isLoading = false,
                        errorMessage = null
                    )
                } else {
                    profileState = profileState.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(response.errorBody()?.string(), "Failed to load profile")
                    )
                }
            } catch (e: Exception) {
                profileState = profileState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Something went wrong"
                )
            }
        }
    }

    /** Updates username and email on the backend. */
    fun updateProfile(username: String, email: String, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            profileState = profileState.copy(isLoading = true, errorMessage = null, successMessage = null)
            try {
                val response = AuthRetrofitInstance.api.updateProfile(
                    UpdateProfileRequest(username = username.trim(), email = email.trim())
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    profileState = profileState.copy(
                        username = body?.username ?: username.trim(),
                        email = body?.email ?: email.trim(),
                        isLoading = false,
                        successMessage = "Profile updated"
                    )
                    onDone()
                } else {
                    profileState = profileState.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(response.errorBody()?.string(), "Failed to update profile")
                    )
                }
            } catch (e: Exception) {
                profileState = profileState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Something went wrong"
                )
            }
        }
    }

    /** Changes the user's password on the backend. */
    fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String,
        onDone: () -> Unit = {}
    ) {
        viewModelScope.launch {
            profileState = profileState.copy(isLoading = true, errorMessage = null, successMessage = null)
            try {
                val response = AuthRetrofitInstance.api.changePassword(
                    ChangePasswordRequest(currentPassword, newPassword, confirmPassword)
                )
                if (response.isSuccessful) {
                    profileState = profileState.copy(
                        isLoading = false,
                        successMessage = "Password changed successfully"
                    )
                    onDone()
                } else {
                    profileState = profileState.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(response.errorBody()?.string(), "Failed to change password")
                    )
                }
            } catch (e: Exception) {
                profileState = profileState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun clearMessages() {
        profileState = profileState.copy(errorMessage = null, successMessage = null)
    }

    private fun getErrorMessage(errorJson: String?, fallback: String): String {
        if (errorJson.isNullOrBlank()) return fallback
        return try {
            JSONObject(errorJson).optString("message", fallback)
        } catch (e: Exception) {
            fallback
        }
    }
}
