package com.altmanifest.secondtake.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.application.GenreAccessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GenreUiState(
    val selectedGenre: String? = null,
    val availableGenres: List<String> = emptyList(),
)

class GenreViewModel(private val genreAccessor: GenreAccessor) : ViewModel() {
    private val _uiState = MutableStateFlow(GenreUiState())
    val uiState = _uiState.asStateFlow()

    fun loadGenres() {
        _uiState.update {
            it.copy(
                availableGenres = genreAccessor.getAvailableGenres().map { genre -> genre.value }
            )
        }
    }
}