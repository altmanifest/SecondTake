package com.altmanifest.secondtake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.domain.Title
import com.altmanifest.secondtake.mock.MockForgottenTitleSource
import com.altmanifest.secondtake.mock.MockTitleOwner

data class ForgottenTitlesUiState(
    val forgottenTitles: List<Title> = emptyList()
)

class ForgottenTitlesViewModel(
    private val titleOwner: MockTitleOwner,
    private val forgottenTitleSource: MockForgottenTitleSource
) : ViewModel() {
    var uiState by mutableStateOf(ForgottenTitlesUiState())
        private set

    fun startViewModel() {
        refreshTitles()
    }

    fun onRestore(title: Title) {
        forgottenTitleSource.delete(title)
        refreshTitles()
    }

    fun onDelete(title: Title) {
        // Currently TitleOwner does not support permanent deletion.
        // This action could be implemented later.
        // For now, we might want to just unforget it or do nothing.
    }

    private fun refreshTitles() {
        val allTitles = titleOwner.getAll()
        val forgottenTitles = allTitles.filter { forgottenTitleSource.get(it.id) != null }
        uiState = uiState.copy(
            forgottenTitles = forgottenTitles
        )
    }
}