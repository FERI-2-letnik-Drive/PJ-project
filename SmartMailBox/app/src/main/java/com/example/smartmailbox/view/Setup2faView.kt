package com.example.smartmailbox.view

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.smartmailbox.ui.theme.Alata
import com.example.smartmailbox.ui.theme.ErrorRed
import com.example.smartmailbox.ui.theme.ForestGreen
import com.example.smartmailbox.ui.theme.VeryDarkGreen
import com.example.smartmailbox.viewmodel.HomeViewModel
import java.io.File

/**
 * Screen for enabling 2FA: captures a reference face photo with the front
 * camera and sends it to the backend.
 */
@Composable
fun Setup2faView(
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    onDone: () -> Unit,
    onBack: () -> Unit
) {
    val state = homeViewModel.homeState
    val context = LocalContext.current

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    var cameraStarted by remember { mutableStateOf(false) }

    LaunchedEffect(state.twoFactorEnabled) {
        if (state.twoFactorEnabled) {
            onDone()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Enable Face 2FA", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (cameraStarted) {
                "Position your face in the camera and tap Capture."
            } else {
                "Tap Start Camera to capture your reference face."
            },
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            if (cameraStarted) {
                FaceCameraPreview(
                    imageCapture = imageCapture,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (state.errorMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = state.errorMessage,
                fontFamily = Alata,
                fontSize = 14.sp,
                color = ErrorRed,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (!cameraStarted) {
                    cameraStarted = true
                } else {
                    captureReferenceImage(
                        context = context,
                        imageCapture = imageCapture,
                        onImageCaptured = { imageFile -> homeViewModel.enableTwoFactor(imageFile) },
                        onError = { message -> homeViewModel.setError(message) }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ForestGreen,
                contentColor = VeryDarkGreen
            ),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
            } else {
                Text(
                    text = if (cameraStarted) "Capture" else "Start Camera",
                    fontFamily = Alata,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ErrorRed,
                contentColor = Color.White
            ),
            enabled = !state.isLoading
        ) {
            Text(text = "Back", fontFamily = Alata, fontWeight = FontWeight.Bold)
        }
    }
}

private fun captureReferenceImage(
    context: Context,
    imageCapture: ImageCapture,
    onImageCaptured: (File) -> Unit,
    onError: (String) -> Unit
) {
    val imageFile = File(context.cacheDir, "ref_face_${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onImageCaptured(imageFile)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception.message ?: "Failed to capture image")
            }
        }
    )
}
