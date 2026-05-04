package com.example.smartmailbox

import android.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartmailbox.buttons.OpenMailboxButton
import com.example.smartmailbox.buttons.ScanQrButton
import com.example.smartmailbox.ui.theme.SmartMailBoxTheme

@Preview()
@Composable
fun App() {

    var isBoxSelected by remember { mutableStateOf(false) }

    SmartMailBoxTheme {
        Scaffold(
            topBar = { TopAppBar() },
            bottomBar = { AppFooter() }
        )

        { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                if (isBoxSelected) {
                    OpenMailboxButton(Modifier.align(Alignment.BottomCenter))
                } else {
                    ScanQrButton(Modifier.align(Alignment.BottomCenter))
                }
            }
        }
    }
}
