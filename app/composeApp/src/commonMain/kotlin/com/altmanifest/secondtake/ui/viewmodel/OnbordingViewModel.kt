package com.altmanifest.secondtake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altmanifest.secondtake.ui.DefinedProvider
import com.altmanifest.secondtake.ui.Provider
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val providers: List<AvailableProvider> = listOf()
)

class OnboardingViewmodel(val source: ConnectedProviderSource) : ViewModel() {
    private val providers = listOf(
        AvailableProvider.Planned(DefinedProvider.IMDB),
        AvailableProvider.Planned(DefinedProvider.FILMWEB),
        AvailableProvider.Planned(DefinedProvider.ONLYFILMS),
        AvailableProvider.Disconnected(DefinedProvider.MOCKIFY)
        )

    var uiState by mutableStateOf(OnboardingUiState())
        private set


    init {
        viewModelScope.launch {
            loadProviders()
        }
    }

    suspend fun loadProviders() {
        val providers = source.getAll()
        val existingIds = providers.map { it.id }
        val disconnectedProviders = this.providers.filter { it.id !in existingIds }

       uiState = uiState.copy(providers = providers + disconnectedProviders)
    }
}

sealed class AvailableProvider : Provider {
    data class Planned(override val id: DefinedProvider, override val isActive: Boolean = false) : AvailableProvider()
    data class Disconnected(
        override val id: DefinedProvider,
        override val isActive: Boolean = true
    ) : AvailableProvider()
}
