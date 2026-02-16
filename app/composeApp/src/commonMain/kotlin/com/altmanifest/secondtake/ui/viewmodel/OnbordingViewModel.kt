package com.altmanifest.secondtake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.ui.DefinedProvider
import com.altmanifest.secondtake.ui.Provider

data class OnboardingUiState(
    val providers: List<Provider> = listOf()
)

class OnboardingViewmodel : ViewModel() {
    var uiState by mutableStateOf(OnboardingUiState())
        private set

    init {
        uiState = OnboardingUiState(
            providers = listOf(
                Provider.Connected(DefinedProvider.IMDB),
                Provider.Connected(DefinedProvider.FILMWEB),
                Provider.Disconnected(DefinedProvider.ONLYFILMS) {},
                Provider.Disconnected(DefinedProvider.MOCKIFY) {}
            )
        )
    }
}
