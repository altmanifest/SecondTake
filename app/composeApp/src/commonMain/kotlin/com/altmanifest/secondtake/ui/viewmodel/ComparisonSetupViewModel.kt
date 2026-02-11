package com.altmanifest.secondtake.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ContentType {
    MOVIE, SHOW, EPISODE, NONE
}

data class ComparisonSetupUiState(
    var selectedContentType: ContentType = ContentType.NONE,
    val selectedGenre: String? = null,
    val selectedShow: String? = null,
    val selectedSeason: String? = null,
    val availableGenres: List<String> = emptyList(),
    val availableShows: List<String> = emptyList(),
    val availableSeasons: List<String> = emptyList()
)

class ComparisonSetupViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ComparisonSetupUiState())
    val uiState: StateFlow<ComparisonSetupUiState> = _uiState.asStateFlow()

    // TODO: These lists should come from a Repository/Model in the future.
    private val allGenres = listOf(
        "Action", "Comedy", "Drama", "Fantasy", "Horror",
        "Mystery", "Romance", "Sci-Fi", "Thriller", "Western",
        "Documentary", "Animation", "Crime", "Historical"
    )

    private val allShows = listOf(
        "Breaking Bad", "Stranger Things", "The Crown", "The Mandalorian", "The Boys",
        "Succession", "The Witcher", "Dark", "Ozark", "Ted Lasso",
        "Severance", "The Bear", "Shogun", "Yellowstone"
    )

    private val allSeasons = listOf("Season 1", "Season 2", "Season 3")

    init {
        _uiState.update {
            it.copy(
                availableGenres = allGenres,
                availableShows = allShows,
                availableSeasons = allSeasons
            )
        }
    }

    fun selectContentType(type: ContentType) {
        _uiState.update { it.copy(selectedContentType = type) }
    }

    fun selectGenre(genre: String) {
        _uiState.update { it.copy(selectedGenre = genre) }
    }

    fun selectShow(show: String) {
        _uiState.update { it.copy(selectedShow = show) }
    }

    fun selectSeason(season: String) {
        _uiState.update { it.copy(selectedSeason = season) }
    }

    // TODO: Implement a function to submit the setup data to the backend/model to create a session.
    fun submitSetup() {
        val state = _uiState.value
    }
}