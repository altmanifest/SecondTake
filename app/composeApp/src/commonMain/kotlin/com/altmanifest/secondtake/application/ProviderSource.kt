package com.altmanifest.secondtake.application


interface ProviderSource {
    suspend fun getAll(): List<AvailableProvider>
    suspend fun save(provider: AvailableProvider)
}