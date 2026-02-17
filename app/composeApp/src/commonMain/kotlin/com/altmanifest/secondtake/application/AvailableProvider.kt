package com.altmanifest.secondtake.application

sealed class AvailableProvider : Provider {
    data class Connected(override val id: DefinedProvider, override val isActive: Boolean = false) : AvailableProvider()
    data class Planned(override val id: DefinedProvider, override val isActive: Boolean = false) : AvailableProvider()
    data class Disconnected(
        override val id: DefinedProvider,
        override val isActive: Boolean = true
    ) : AvailableProvider()
}