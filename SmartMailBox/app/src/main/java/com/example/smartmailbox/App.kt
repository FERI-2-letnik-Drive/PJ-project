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

    val mailboxViewModel: MailBoxViewModel = viewModel()

    SmartMailBoxTheme {
        Scaffold(
            topBar = { TopAppBar() },
            bottomBar = { AppFooter() }
        ) { paddingValues ->
            MailBoxView(mailboxViewModel, paddingValues)
            // HomeView(paddingValues = paddingValues)
            // LogsView(paddingValues = paddingValues)
        }
    }
}
