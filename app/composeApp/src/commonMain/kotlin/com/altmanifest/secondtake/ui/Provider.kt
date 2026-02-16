package com.altmanifest.secondtake.ui

sealed interface Provider {
    val id: DefinedProvider
    val isActive: Boolean

    data class Connected(override val id: DefinedProvider, override val isActive: Boolean = false) : Provider
    data class Disconnected(
        override val id: DefinedProvider,
        override val isActive: Boolean = true,
        val onConnect: () -> Unit
    ) : Provider
}
