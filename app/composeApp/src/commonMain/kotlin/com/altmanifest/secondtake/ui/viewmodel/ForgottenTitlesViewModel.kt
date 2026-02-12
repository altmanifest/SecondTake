package com.altmanifest.secondtake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.application.ForgottenTitleSource
import com.altmanifest.secondtake.domain.Title

data class ForgottenTitlesUiState(
    val forgottenTitles: List<Title> = emptyList()
)

class ForgottenTitlesViewModel(
    private val forgottenTitleSource: ForgottenTitleSource
) : ViewModel() {
    var uiState by mutableStateOf(ForgottenTitlesUiState())
        private set

    suspend fun startViewModel() {
        refreshTitles()
    }

    suspend fun onRestore(title: Title) {
        forgottenTitleSource.delete(title)
        refreshTitles()
    }

    fun onDelete(title: Title) {
        // Currently TitleOwner does not support permanent deletion.
        // This action could be implemented later.
        // For now, we might want to just unforget it or do nothing.
    }

    private suspend fun refreshTitles() {
        val forgottenTitles = forgottenTitleSource.getAll().toList()
        uiState = uiState.copy(
            forgottenTitles = forgottenTitles
        )
    }
}