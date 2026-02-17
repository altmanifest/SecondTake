package com.altmanifest.secondtake.application

import kotlinx.serialization.Serializable

@Serializable
sealed class DefinedProvider {
    @Serializable
    object Imdb : DefinedProvider()
    @Serializable
    object Filmweb : DefinedProvider()
    @Serializable
    object Onlyfilms : DefinedProvider()
    @Serializable
    object Mockify : DefinedProvider()
}