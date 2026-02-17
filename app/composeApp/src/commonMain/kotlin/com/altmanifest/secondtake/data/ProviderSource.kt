package com.altmanifest.secondtake.data

import com.altmanifest.secondtake.application.AvailableProvider
import com.altmanifest.secondtake.application.ProviderSource
import com.altmanifest.secondtake.application.DefinedProvider

class ProviderSource(private val store: Store) : ProviderSource {
    override suspend fun getAll(): List<AvailableProvider> = store.get().connectedProviders.mapNotNull {
        try {
            AvailableProvider.Connected(DefinedProvider.valueOf(it))
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    override suspend fun save(provider: AvailableProvider) = store.update { state ->
        state.connectedProviders.add(provider.id.name)
    }
}