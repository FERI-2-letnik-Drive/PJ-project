package com.example.smartmailbox

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartmailbox.navigation.NavigationScreen
import com.example.smartmailbox.ui.theme.SmartMailBoxTheme
import com.example.smartmailbox.view.HomeView
import com.example.smartmailbox.view.LogView
import com.example.smartmailbox.view.MailBoxView
import com.example.smartmailbox.viewmodel.HomeViewModel
import com.example.smartmailbox.viewmodel.LogViewModel
import com.example.smartmailbox.viewmodel.MailBoxViewModel


@Composable
fun App() {
    /* doesn't survive recomposition (screen rotation)
    var mailboxViewModel by remember {
        mutableStateOf(MailboxViewModel())
    }
    */

    val navController = rememberNavController()

    val homeViewModel: HomeViewModel = viewModel()
    val mailBoxViewModel: MailBoxViewModel = viewModel()
    val logModel: LogViewModel = viewModel()

    SmartMailBoxTheme {
        Scaffold(
            topBar = { TopAppBar() },
            bottomBar = { AppFooter(navController = navController) }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = NavigationScreen.Home.route,
                enterTransition = { fadeIn(tween(200)) },
                exitTransition = { fadeOut(tween(200)) },
                popEnterTransition = { fadeIn(tween(200)) },
                popExitTransition = { fadeOut(tween(200)) }
            ) {
                // navigateBack exists
                composable(NavigationScreen.Home.route) { HomeView(homeViewModel, paddingValues) }
                composable(NavigationScreen.Scan.route) { MailBoxView(mailBoxViewModel, paddingValues) }
                composable(NavigationScreen.Log.route) { LogView(logModel, paddingValues) }
            }
            // MailBoxView(mailBoxViewModel, paddingValues)
            // HomeView(paddingValues = paddingValues)
            // LogView(paddingValues = paddingValues)
        }
    }
}
