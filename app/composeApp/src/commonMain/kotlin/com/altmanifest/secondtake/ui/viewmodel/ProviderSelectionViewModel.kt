package com.altmanifest.secondtake.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altmanifest.secondtake.application.DefinedProvider
import com.altmanifest.secondtake.application.Provider
import com.altmanifest.secondtake.application.ProviderSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProviderSelectionUiState(
    val connectedProviders: List<ConnectedProvider> = listOf()
)

class ProviderSelectionViewModel(private val providerSource: ProviderSource) : ViewModel() {
    private val _uiState = MutableStateFlow(ProviderSelectionUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadConnectedProviders()
        }
    }

    private suspend fun loadConnectedProviders() {
        val connectedProviders = providerSource.getAll().map {
            ConnectedProvider(id = it.id, isActive = !it.isActive)
        }
        _uiState.update { it.copy(connectedProviders = connectedProviders) }
    }
}

data class ConnectedProvider(override val id: DefinedProvider, override val isActive: Boolean) : Provider
