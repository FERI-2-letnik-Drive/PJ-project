package com.example.smartmailbox.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmailbox.api.AuthRetrofitInstance
import com.example.smartmailbox.model.LogState
import kotlinx.coroutines.launch

class LogViewModel : ViewModel() {

    var logState by mutableStateOf(LogState())
        private set

    /** Loads the open history for the user's first mailbox. */
    fun loadLogs() {
        viewModelScope.launch {
            logState = logState.copy(isLoading = true, errorMessage = null)
            try {
                val mailboxesResponse = AuthRetrofitInstance.mailboxApi.getMailboxes()
                if (!mailboxesResponse.isSuccessful) {
                    logState = logState.copy(
                        isLoading = false,
                        errorMessage = "Could not load mailboxes"
                    )
                    return@launch
                }

                val mailboxId = mailboxesResponse.body().orEmpty().firstOrNull()?._id
                if (mailboxId == null) {
                    logState = logState.copy(
                        isLoading = false,
                        logs = emptyList(),
                        errorMessage = "No mailbox found"
                    )
                    return@launch
                }

                val logsResponse = AuthRetrofitInstance.mailboxApi.getLogs(mailboxId)
                if (logsResponse.isSuccessful) {
                    logState = logState.copy(
                        isLoading = false,
                        logs = logsResponse.body().orEmpty(),
                        errorMessage = null
                    )
                } else {
                    logState = logState.copy(
                        isLoading = false,
                        errorMessage = "Could not load history"
                    )
                }
            } catch (e: Exception) {
                logState = logState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Something went wrong"
                )
            }
        }
    }
}
