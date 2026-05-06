package com.example.smartmailbox

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartmailbox.ui.theme.SmartMailBoxTheme
import com.example.smartmailbox.view.MailBoxView
import com.example.smartmailbox.viewmodel.MailBoxViewModel


@Composable
fun App() {
    /* doesn't survive recomposition (screen rotation)
    var mailboxViewModel by remember {
        mutableStateOf(MailboxViewModel())
    }
    */

    val mailBoxViewModel: MailBoxViewModel = viewModel()

    SmartMailBoxTheme {
        Scaffold(
            topBar = { TopAppBar() },
            bottomBar = { AppFooter() }
        ) { paddingValues ->
            MailBoxView(mailBoxViewModel, paddingValues)
            // HomeView(paddingValues = paddingValues)
            // LogView(paddingValues = paddingValues)
        }
    }
}
