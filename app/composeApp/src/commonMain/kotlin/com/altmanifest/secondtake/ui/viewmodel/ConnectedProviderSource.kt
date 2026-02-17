package com.altmanifest.secondtake.ui.viewmodel

interface ConnectedProviderSource {
    suspend fun getAll(): List<AvailableProvider>

}