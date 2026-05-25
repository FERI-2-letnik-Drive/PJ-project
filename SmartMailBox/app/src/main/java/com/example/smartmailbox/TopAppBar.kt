package com.example.smartmailbox

import android.hardware.lights.Light
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartmailbox.ui.theme.ForestGreen
import com.example.smartmailbox.ui.theme.LightMint

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit
) {
    val appTitle = stringResource(R.string.app_name)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(ForestGreen)
            .padding(16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = appTitle,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(
            onClick = onProfileClick,
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.account_box),
                contentDescription = "Profile",
                tint = LightMint,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}