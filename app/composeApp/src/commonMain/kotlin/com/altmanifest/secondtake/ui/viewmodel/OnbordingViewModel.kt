package com.altmanifest.secondtake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.ui.DefinedProvider
import com.altmanifest.secondtake.ui.Provider

data class OnboardingUiState(
    val providers: List<AvailableProvider> = listOf()
)

class OnboardingViewmodel : ViewModel() {
    var uiState by mutableStateOf(OnboardingUiState())
        private set

    init {
        uiState = OnboardingUiState(
            providers = listOf(
                AvailableProvider.Connected(DefinedProvider.IMDB),
                AvailableProvider.Connected(DefinedProvider.FILMWEB),
                AvailableProvider.Disconnected(DefinedProvider.ONLYFILMS) {},
                AvailableProvider.Disconnected(DefinedProvider.MOCKIFY) {}
            )
        )
    }
}

sealed class AvailableProvider : Provider {
    data class Connected(override val id: DefinedProvider, override val isActive: Boolean = false) : AvailableProvider()
    data class Disconnected(
        override val id: DefinedProvider,
        override val isActive: Boolean = true,
        val onConnect: () -> Unit
    ) : AvailableProvider()
}
