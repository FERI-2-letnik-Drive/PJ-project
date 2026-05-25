package com.example.smartmailbox.navigation

import androidx.navigation.Navigation

sealed class NavigationScreen(val route: String) {
    object Home : NavigationScreen("home")
    object Scan : NavigationScreen("scan")
    object Log : NavigationScreen("log")
    object Login : NavigationScreen("login")
}