package com.example.smartmailbox.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartmailbox.ui.theme.Alata
import com.example.smartmailbox.ui.theme.Emerald
import com.example.smartmailbox.ui.theme.ForestGreen
import com.example.smartmailbox.ui.theme.VeryDarkGreen
import com.example.smartmailbox.viewmodel.HomeViewModel


@Composable
fun HomeView(
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    onEnable2faClick: () -> Unit = {}
) {
    val state = homeViewModel.homeState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (state.twoFactorEnabled) "Two-factor: Enabled" else "Two-factor: set up below",
            style = MaterialTheme.typography.bodyLarge
        )

        if (state.message != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = state.message,
                color = Emerald,
                fontFamily = Alata,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onEnable2faClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ForestGreen,
                contentColor = VeryDarkGreen
            )
        ) {
            Text(text = "Enable 2FA", fontFamily = Alata, fontWeight = FontWeight.Bold)
        }
    }
}
