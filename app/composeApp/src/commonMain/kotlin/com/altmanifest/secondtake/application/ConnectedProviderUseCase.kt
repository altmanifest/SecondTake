package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.ui.viewmodel.ConnectedProviderUseCase

class ConnectedProviderUseCase(private val source: ConnectedProviderSource, private val config: Config) : ConnectedProviderUseCase {
    override suspend fun getConnectedProviders(): List<AvailableProvider> {
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