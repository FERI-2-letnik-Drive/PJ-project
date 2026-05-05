package com.example.smartmailbox.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartmailbox.view.buttons.OpenMailBoxButton
import com.example.smartmailbox.viewmodel.MailBoxViewModel
import com.example.smartmailbox.view.buttons.ScanQRCodeButton

@Composable
fun MailBoxView(mailBoxViewModel: MailBoxViewModel = viewModel(), paddingValues: PaddingValues) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        if (mailBoxViewModel.scannerState.isScannerRunning) {

            QRCodeScannerView(
                onQrCodeScanned = { mailBoxViewModel.onQrCodeScanned(it) }
            )

            Button(
                onClick = { mailBoxViewModel.cancelScanner() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .height(48.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            ) {
                Text("Cancel")
            }

            if (mailBoxViewModel.scannerState.scannedCode.isNotEmpty()) {
                ScanQRCodeButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onClick = { mailBoxViewModel.stopScannerAndGetScannedCode() }
                )
            }

        } else {
            if (mailBoxViewModel.scannerState.scannedCode.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Mailbox code:")
                    Text(mailBoxViewModel.scannerState.scannedCode)

                    OpenMailBoxButton()
                }
            }

            ScanQRCodeButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                onClick = {
                    mailBoxViewModel.startScanner()
                }
            )
        }
    }
}