package com.example.smartmailbox.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmailbox.ui.theme.Alata
import com.example.smartmailbox.ui.theme.Emerald
import com.example.smartmailbox.ui.theme.ErrorRed
import com.example.smartmailbox.ui.theme.ForestGreen
import com.example.smartmailbox.ui.theme.VeryDarkGreen
import com.example.smartmailbox.viewmodel.ProfileViewModel

@Composable
fun ProfileView(
    profileViewModel: ProfileViewModel,
    paddingValues: PaddingValues,
    onTwoFactorClick: () -> Unit,
    onLogout: () -> Unit = {}
) {
    val profileState = profileViewModel.profileState

    var showEditDialog by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    // Load the real profile from the backend when the screen opens.
    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileInfoRow(label = "Username:", value = profileState.username)

        Spacer(modifier = Modifier.height(12.dp))

        ProfileInfoRow(label = "Email:", value = profileState.email)

        if (profileState.errorMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = profileState.errorMessage,
                fontFamily = Alata,
                fontSize = 14.sp,
                color = ErrorRed,
                fontWeight = FontWeight.Bold
            )
        }

        if (profileState.successMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = profileState.successMessage,
                fontFamily = Alata,
                fontSize = 14.sp,
                color = Emerald,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { showEditDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ForestGreen,
                contentColor = VeryDarkGreen
            ),
            enabled = !profileState.isLoading
        ) {
            Text(text = "Edit Profile", fontFamily = Alata, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showPasswordDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ForestGreen,
                contentColor = VeryDarkGreen
            ),
            enabled = !profileState.isLoading
        ) {
            Text(text = "Change Password", fontFamily = Alata, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ErrorRed,
                contentColor = Color.White
            )
        ) {
            Text(text = "Logout", fontFamily = Alata, fontWeight = FontWeight.Bold)
        }

        if (profileState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            initialUsername = profileState.username,
            initialEmail = profileState.email,
            onConfirm = { username, email ->
                profileViewModel.updateProfile(username, email) { showEditDialog = false }
            },
            onDismiss = { showEditDialog = false }
        )
    }

    if (showPasswordDialog) {
        ChangePasswordDialog(
            onConfirm = { current, new, confirm ->
                profileViewModel.changePassword(current, new, confirm) { showPasswordDialog = false }
            },
            onDismiss = { showPasswordDialog = false }
        )
    }
}

@Composable
private fun EditProfileDialog(
    initialUsername: String,
    initialEmail: String,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var username by remember { mutableStateOf(initialUsername) }
    var email by remember { mutableStateOf(initialEmail) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(username, email) }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun ChangePasswordDialog(
    onConfirm: (String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var current by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Password") },
        text = {
            Column {
                OutlinedTextField(
                    value = current,
                    onValueChange = { current = it },
                    label = { Text("Current password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirm,
                    onValueChange = { confirm = it },
                    label = { Text("Confirm password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(current, newPassword, confirm) }) { Text("Change") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun ProfileInfoRow(
    label: String,
    value: String,
    valueColor: Color = VeryDarkGreen // default color
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 0.dp, bottom = 5.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(horizontal = 12.dp, vertical = 14.dp)
        )
    }
}
