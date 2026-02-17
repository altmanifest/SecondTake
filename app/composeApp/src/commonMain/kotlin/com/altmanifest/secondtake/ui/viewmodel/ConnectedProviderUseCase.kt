package com.altmanifest.secondtake.ui.viewmodel

import com.altmanifest.secondtake.application.AvailableProvider

interface ConnectedProviderUseCase {
    suspend fun getConnectedProviders(): List<AvailableProvider>
    suspend fun connectProvider(provider: AvailableProvider)

}