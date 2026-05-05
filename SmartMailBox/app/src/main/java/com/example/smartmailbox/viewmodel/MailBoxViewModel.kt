package com.example.smartmailbox.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel

class MailBoxViewModel : ViewModel() {
    var scannedCode by mutableStateOf("")
        private set

    var isScannerRunning by mutableStateOf(false)
        private set

    fun onQrCodeScanned(code: String) {
        scannedCode = code
    }

    fun startScanner() {
        scannedCode = ""
        isScannerRunning = true
    }

    fun cancelScanner() {
        scannedCode = ""
        isScannerRunning = false
    }

    fun stopScanner() {
        isScannerRunning = false
    }

    fun openMailbox() {
        //api

    }
}