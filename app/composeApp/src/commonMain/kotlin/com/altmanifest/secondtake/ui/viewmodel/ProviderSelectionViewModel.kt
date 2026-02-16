package com.altmanifest.secondtake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.altmanifest.secondtake.ui.DefinedProvider
import com.altmanifest.secondtake.ui.Provider

data class ProviderSelectionUiState(
    val connectedProviders: List<ConnectedProvider> = listOf()
)

class ProviderSelectionViewModel : ViewModel() {
    var uiState by mutableStateOf(ProviderSelectionUiState())
        private set

    init {
        uiState = ProviderSelectionUiState(
            connectedProviders = listOf(
                ConnectedProvider(DefinedProvider.MOCKIFY)
            )
        )
    }
}

data class ConnectedProvider(override val id: DefinedProvider, override val isActive: Boolean = true) : Provider
