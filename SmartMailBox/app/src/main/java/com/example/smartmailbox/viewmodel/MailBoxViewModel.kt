package com.example.smartmailbox.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmailbox.api.MailBoxAPI
import com.example.smartmailbox.api.PostMailBoxData
import com.example.smartmailbox.api.RetrofitInstance
import com.example.smartmailbox.model.APIState
import com.example.smartmailbox.model.MailBoxState
import com.example.smartmailbox.model.ScannerState
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MailBoxViewModel : ViewModel() {
    var scannerState by mutableStateOf(ScannerState())
        private set

    var mailBoxState by mutableStateOf(MailBoxState())
        private set

    var apiState by mutableStateOf(APIState())
        private set

    fun onQrCodeScanned(code: String) {
        /*
        Safety check. Camera can capture even after I call cancelScanner()
        Camera runs on background thread and proccess 30-60FPS
        Camera overrode my scannedCode because camera ran
        after cancelScanner()
        */
        if (scannerState.isScannerRunning) {
            scannerState = scannerState.copy(scannedCode = code)
        }
    }

    fun startScanner() {
        scannerState = ScannerState(isScannerRunning = true)
    }

    fun cancelScanner() {
        scannerState = ScannerState()
    }

    fun stopScannerAndGetScannedCode() {
        scannerState = scannerState.copy(isScannerRunning = false)
    }

    private fun extractMailBoxId(url: String): Int {
        val segments = url.trimEnd('/').split("/")
        return segments[4].toInt()
    }

    private fun createPostMailBoxData(url: String) : PostMailBoxData = PostMailBoxData(
        boxId = extractMailBoxId(url),
        tokenFormat = 2 // wavzip
    )


    fun openMailbox() {
        viewModelScope.launch {
            apiState = apiState.copy(isLoading = true)
            try {
                val data = createPostMailBoxData(scannerState.scannedCode)
                // call API
                val response = RetrofitInstance.api.postMailBoxData(
                    data
                )

                apiState = apiState.copy(response = response.body())
                //mailBoxState = MailBoxState(isMailBoxOpen = true)
                Log.d("MailBoxAPI", "Response: $response")
                Log.d("MailBoxAPI", "Response object: ${response.body()}")

                // TODO: DECODE HERE (methods pls)

            } catch(e: Exception) {
                apiState = APIState(isLoading = false, error = e.toString())
            }
        }
    }
}