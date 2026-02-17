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
    private val providers = mapOf(
        DefinedProvider.IMDB to AvailableProvider.Planned(DefinedProvider.IMDB),
        DefinedProvider.FILMWEB to AvailableProvider.Planned(DefinedProvider.FILMWEB),
        DefinedProvider.ONLYFILMS to AvailableProvider.Planned(DefinedProvider.ONLYFILMS),
        DefinedProvider.MOCKIFY to AvailableProvider.Disconnected(DefinedProvider.MOCKIFY) {}
    )

    var uiState by mutableStateOf(OnboardingUiState())
        private set


    init {
        uiState = OnboardingUiState(
            providers = providers.values.toList()
        )
    }
}

sealed class AvailableProvider : Provider {
    data class Planned(override val id: DefinedProvider, override val isActive: Boolean = false) : AvailableProvider()
    data class Disconnected(
        override val id: DefinedProvider,
        override val isActive: Boolean = true,
        val onConnect: () -> Unit
    ) : AvailableProvider()
}
