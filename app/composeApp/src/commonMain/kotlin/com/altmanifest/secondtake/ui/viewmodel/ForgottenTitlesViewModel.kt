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

    fun onRestore(): Nothing = TODO("Will be implemented once the first provider is connected")

    suspend fun onDelete(title: Title) {
        forgottenTitleSource.delete(title)
        refreshTitles()
    }

    private suspend fun refreshTitles() {
        val forgottenTitles = forgottenTitleSource.getAll().toList()
        uiState = uiState.copy(
            forgottenTitles = forgottenTitles
        )
    }
}