package com.example.smartmailbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartmailbox.ui.theme.ForestGreen

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {
    val appTitle = stringResource(R.string.app_name)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(ForestGreen)
            .padding(20.dp)
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = appTitle,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}