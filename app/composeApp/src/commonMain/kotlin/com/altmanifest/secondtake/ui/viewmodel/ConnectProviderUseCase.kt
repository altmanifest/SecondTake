package com.altmanifest.secondtake.ui.viewmodel

import com.altmanifest.secondtake.application.AvailableProvider

interface ConnectProviderUseCase {
    suspend fun getAvailableProviders(): List<AvailableProvider>
    suspend fun connectProvider(provider: AvailableProvider)

}