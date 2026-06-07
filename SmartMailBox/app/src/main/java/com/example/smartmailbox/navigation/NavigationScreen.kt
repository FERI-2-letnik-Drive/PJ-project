package com.example.smartmailbox.navigation

import androidx.navigation.Navigation

sealed class NavigationScreen(val route: String) {
    object Startup : NavigationScreen("startup")
    object Home : NavigationScreen("home")
    object Scan : NavigationScreen("scan")
    object Log : NavigationScreen("log")
    object Login : NavigationScreen("login")
    object Profile : NavigationScreen("profile")
    object FaceVerify : NavigationScreen("face_verify")
    object Register : NavigationScreen("register")
    object Setup2fa : NavigationScreen("setup_2fa")
}