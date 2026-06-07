package com.example.smartmailbox.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmailbox.api.AuthRetrofitInstance
import com.example.smartmailbox.model.HomeState
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class HomeViewModel : ViewModel() {

    var homeState by mutableStateOf(HomeState())
        private set

    /** Uploads a reference face image and turns on 2FA for the user. */
    fun enableTwoFactor(imageFile: File, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            homeState = homeState.copy(isLoading = true, errorMessage = null, message = null)
            try {
                val requestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData(
                    name = "image",
                    filename = imageFile.name,
                    body = requestBody
                )

                val response = AuthRetrofitInstance.api.enableTwoFactor(imagePart)

                if (response.isSuccessful && response.body()?.twoFactorEnabled == true) {
                    homeState = homeState.copy(
                        isLoading = false,
                        twoFactorEnabled = true,
                        message = "Two-factor authentication enabled"
                    )
                    onSuccess()
                } else {
                    homeState = homeState.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(response.errorBody()?.string())
                    )
                }
            } catch (e: Exception) {
                homeState = homeState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun setError(message: String) {
        homeState = homeState.copy(isLoading = false, errorMessage = message)
    }

    private fun getErrorMessage(errorJson: String?): String {
        if (errorJson.isNullOrBlank()) return "Failed to enable 2FA"
        return try {
            JSONObject(errorJson).optString("message", "Failed to enable 2FA")
        } catch (e: Exception) {
            "Failed to enable 2FA"
        }
    }
}
