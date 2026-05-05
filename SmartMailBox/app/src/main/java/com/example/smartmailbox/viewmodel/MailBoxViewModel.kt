package com.example.smartmailbox.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.smartmailbox.model.ScannerState

class MailBoxViewModel : ViewModel() {
    var scannerState by mutableStateOf(ScannerState())
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

    fun openMailbox() {
        //api

    }
}