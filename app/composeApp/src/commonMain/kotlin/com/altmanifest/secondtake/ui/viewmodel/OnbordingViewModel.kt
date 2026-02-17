package com.altmanifest.secondtake.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altmanifest.secondtake.application.AvailableProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val providers: List<AvailableProvider> = listOf(),
    val isOneProviderConnected: Boolean = false
)

class OnboardingViewmodel(val useCase: ConnectedProviderUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadProviders()
        }
    }

    suspend fun loadProviders() {
        val providers = useCase.getConnectedProviders()
        _uiState.update { it.copy(
            providers = providers,
            isOneProviderConnected = providers.any { provider -> provider is AvailableProvider.Connected }
        ) }
    }

    suspend fun connectProvider(provider: AvailableProvider) {
        useCase.connectProvider(provider)
        loadProviders()
    }
}
