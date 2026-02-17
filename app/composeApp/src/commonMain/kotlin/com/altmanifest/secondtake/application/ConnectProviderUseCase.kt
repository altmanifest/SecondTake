package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.ui.viewmodel.ConnectProviderUseCase

class ConnectProviderUseCase(private val source: ProviderSource, private val config: Config) : ConnectProviderUseCase {
    override suspend fun getAvailableProviders(): List<AvailableProvider> {
        val connectedProviders = source.getAll()
        val connectedProviderIds = connectedProviders.map { it.id }

        val disconnectedProviders = config.availableProviders.filter { it.id !in connectedProviderIds }
        return disconnectedProviders + connectedProviders
    }

    override suspend fun connectProvider(provider: AvailableProvider) {
        source.save(provider)
    }

    data class Config(val availableProviders: List<AvailableProvider>)
}