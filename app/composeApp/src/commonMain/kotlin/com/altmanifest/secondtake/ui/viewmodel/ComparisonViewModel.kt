package com.altmanifest.secondtake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.altmanifest.secondtake.Routes
import com.altmanifest.secondtake.application.CompareTitlesUseCase
import com.altmanifest.secondtake.application.CompareTitlesUseCase.Setup
import com.altmanifest.secondtake.domain.Choice
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Genre
import com.altmanifest.secondtake.domain.Preference
import com.altmanifest.secondtake.domain.Session

data class ComparisonUIState(
    val currentComparison: Comparison.View? = null,
    val currentStep: Int = 0,
    val totalSteps: Int = 0,
    val isFinished: Boolean = false
)

class ComparisonViewModel(
    private val useCase: CompareTitlesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    var uiState by mutableStateOf(ComparisonUIState())
        private set

    suspend fun startViewModel(){
        val comparison = savedStateHandle.toRoute<Routes.Comparison>()

        // where was not a simple way to pass GenreSelection to the Route
        // I decided to pass just the Text selected by the previous step
        // To not mix the layers I decided to just check for the string which is not ideal
        val setup = if (comparison.genre == "All") {
            Setup.Default
        } else {
            Setup.ByGenre(Genre(comparison.genre))
        }

        val result = useCase.start(setup)
        when (result) {
            CompareTitlesUseCase.CreateResult.NoComparisons -> {
                uiState = uiState.copy(
                    isFinished = true
                )
            }
            CompareTitlesUseCase.CreateResult.NoTitles -> {
                uiState = uiState.copy(
                    isFinished = true
                )
            }
            is CompareTitlesUseCase.CreateResult.Success -> {
                val snapshot = result.initialSnapshot
                uiState = uiState.copy(
                    currentComparison = snapshot.comparison,
                    currentStep = snapshot.progress.current,
                    totalSteps = snapshot.progress.total
                )
            }
        }
    }

    suspend fun onRate(preference: Preference, ratingStrength: Comparison.Rating.Strength) {
        val action = Session.Action.Rate(preference, ratingStrength)
        val result = useCase.handle(action)

        when (result) {
            is CompareTitlesUseCase.State.Running -> {
                uiState = uiState.copy(
                    currentComparison = result.snapshot.comparison,
                    currentStep = result.snapshot.progress.current,
                    totalSteps = result.snapshot.progress.total
                )
            }
            CompareTitlesUseCase.State.Finished -> {
                uiState = uiState.copy(
                    isFinished = true,
                    currentStep = uiState.totalSteps,
                )
            }
        }
    }

    suspend fun onSkip() {
        val result = useCase.handle(Session.Action.Skip)
        updateState(result)
    }

    suspend fun onForget(choice: Choice) {
        val result = useCase.handle(Session.Action.Forget(choice))
        updateState(result)
    }

    fun updateState(result: CompareTitlesUseCase.State) {
        when (result) {
            is CompareTitlesUseCase.State.Running -> {
                uiState = uiState.copy(
                    currentComparison = result.snapshot.comparison,
                    currentStep = result.snapshot.progress.current,
                    totalSteps = result.snapshot.progress.total
                )
            }
            CompareTitlesUseCase.State.Finished -> {
                println(result)
                uiState = uiState.copy(
                    isFinished = true,
                    currentStep = uiState.totalSteps,
                )
            }
        }
    }
}