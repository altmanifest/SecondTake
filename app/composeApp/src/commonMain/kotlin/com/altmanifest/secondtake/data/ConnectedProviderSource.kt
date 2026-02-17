package com.altmanifest.secondtake.data

import com.altmanifest.secondtake.application.AvailableProvider
import com.altmanifest.secondtake.application.ConnectedProviderSource
import com.altmanifest.secondtake.application.DefinedProvider

class ConnectedProviderSource(private val store: Store) : ConnectedProviderSource {
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