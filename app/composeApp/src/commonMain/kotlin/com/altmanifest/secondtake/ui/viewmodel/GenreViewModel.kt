package com.altmanifest.secondtake.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.application.GenreAccessor
import com.altmanifest.secondtake.domain.Genre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GenreUiState(
    val selectedGenre: GenreSelection? = null,
    val isSelected: Boolean = false,
    val availableGenres: List<GenreSelection> = listOf(),
)

class GenreViewModel(private val genreAccessor: GenreAccessor) : ViewModel() {
    private val _uiState = MutableStateFlow(GenreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadGenres()
    }

    fun loadGenres() {
        val ones = genreAccessor.getAvailableGenres().map { GenreSelection.One(it) }
        _uiState.update {
            it.copy(
                availableGenres = listOf(GenreSelection.All) + ones,
            )
        }
    }

    fun selectGenre(genre: GenreSelection) {
        _uiState.update {
            it.copy(
                selectedGenre = genre,
                isSelected = true,
            )
        }
    }
}

sealed class GenreSelection {
    object All : GenreSelection()
    data class One(val genre: Genre) : GenreSelection()
}