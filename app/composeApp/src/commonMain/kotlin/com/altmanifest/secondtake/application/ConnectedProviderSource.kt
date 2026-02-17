package com.altmanifest.secondtake.application

interface ConnectedProviderSource {
    suspend fun getAll(): List<AvailableProvider>
    suspend fun save(provider: AvailableProvider)
}