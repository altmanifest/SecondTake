package com.altmanifest.secondtake.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.application.GenreAccessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GenreUiState(
    val selectedGenre: GenreSelection? = null,
    val availableGenres: List<GenreSelection> = listOf(),
)

class GenreViewModel(private val genreAccessor: GenreAccessor) : ViewModel() {
    private val _uiState = MutableStateFlow(GenreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadGenres()
    }

    fun loadGenres() {
        val genres = genreAccessor.getAvailableGenres().map { GenreSelection.Genre(it.value) }
        _uiState.update {
            it.copy(
                availableGenres = listOf(GenreSelection.All) + genres,
            )
        }
    }
}

sealed class GenreSelection {
    object All : GenreSelection()
    data class Genre(val genre: String) : GenreSelection()
}