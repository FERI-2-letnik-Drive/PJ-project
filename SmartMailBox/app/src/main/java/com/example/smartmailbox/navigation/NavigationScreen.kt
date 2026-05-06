package com.example.smartmailbox.navigation

sealed class NavigationScreen(val route: String) {
    object Home : NavigationScreen("home")
    object Scan : NavigationScreen("scan")
    object Log : NavigationScreen("log")
}