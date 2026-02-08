package com.altmanifest.secondtake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.application.CompareTitlesUseCase
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Preference
import com.altmanifest.secondtake.domain.Session

data class ComparisonUIState(
    val currentComparison: Comparison.View? = null,
    val currentStep: Int = 0,
    val totalSteps: Int = 0,
    val isFinished: Boolean = false
)

class ComparisonViewModel(
    private val useCase: CompareTitlesUseCase
) : ViewModel() {


    var uiState by mutableStateOf(ComparisonUIState())
        private set

    /**init {
        val result = useCase.start()
        when (result) {
            CompareTitlesUseCase.CreateResult.NoComparisons -> println("NO Comparisons")
            CompareTitlesUseCase.CreateResult.NoTitles -> println("No Titles")
            is CompareTitlesUseCase.CreateResult.Success -> {
                val snapshot = result.initialSnapshot
                uiState = uiState.copy(
                    currentComparison = snapshot.comparison,
                    currentStep = snapshot.progress.current,
                    totalSteps = snapshot.progress.total
                )
            }
        }
    }*/

    fun startViewModel(){
        val result = useCase.start()
        when (result) {
            CompareTitlesUseCase.CreateResult.NoComparisons -> println("NO Comparisons")
            CompareTitlesUseCase.CreateResult.NoTitles -> println("No Titles")
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

    fun onRate(preference: Preference, ratingStrength: Comparison.Rating.Strength) {
        val action = Session.Action.Rate(preference, ratingStrength)
        val result = useCase.handle(action) 

        when (result) {
            is CompareTitlesUseCase.State.Running -> {
                uiState = uiState.copy(
                    currentComparison = result.snapshot.comparison,
                    currentStep = result.snapshot.progress.current,
                    totalSteps = result.snapshot.progress.total
                )
                println(result )
                println(uiState)
            }
            is CompareTitlesUseCase.State.Finished -> {
                uiState = uiState.copy(
                    isFinished = true,
                    currentStep = uiState.totalSteps,
                )
            }
        }
    }
}