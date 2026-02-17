package com.altmanifest.secondtake

import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object Onboarding
    @Serializable
    object Start
    @Serializable
    object ProviderSelection
    @Serializable
    object ForgottenTitles
    @Serializable
    data class Comparison(val genre: String)
    @Serializable
    object ContentTypeMovieShow
    @Serializable
    object ContentTypeShowEpisode
    @Serializable
    object Genre
    @Serializable
    object SelectShow
    @Serializable
    object SelectSeason
}