package com.example.smartmailbox.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.smartmailbox.viewmodel.HomeViewModel


@Composable
fun HomeView(homeViewModel: HomeViewModel, paddingValues: PaddingValues) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        Text("HomeView")
    }
}