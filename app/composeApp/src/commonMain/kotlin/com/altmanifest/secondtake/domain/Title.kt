package com.altmanifest.secondtake.domain

import kotlinx.serialization.Serializable

@Serializable
data class Title(val id: Id, val value: String, val posterUrl: String, val genre: Genre, val rating: Rating) {
    @Serializable
    value class Id(val value: String)
}