package com.altmanifest.secondtake.data

import com.altmanifest.secondtake.application.AvailableProvider
import com.altmanifest.secondtake.application.ProviderSource

class ProviderSource(private val store: Store) : ProviderSource {
    override suspend fun getAll(): List<AvailableProvider> = store.get().connectedProviders.map {
        AvailableProvider.Connected(it.id)
    }

    override suspend fun save(provider: AvailableProvider) = store.update { state ->
        state.connectedProviders.add(provider)
    }
}