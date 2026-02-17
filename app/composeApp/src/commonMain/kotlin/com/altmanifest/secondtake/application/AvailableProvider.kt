package com.altmanifest.secondtake.application

import kotlinx.serialization.Serializable

@Serializable
sealed class AvailableProvider : Provider {
    @Serializable
    data class Connected(override val id: DefinedProvider, override val isActive: Boolean = false) : AvailableProvider()
    @Serializable
    data class Planned(override val id: DefinedProvider, override val isActive: Boolean = false) : AvailableProvider()
    @Serializable
    data class Disconnected(
        override val id: DefinedProvider,
        override val isActive: Boolean = true
    ) : AvailableProvider()
}