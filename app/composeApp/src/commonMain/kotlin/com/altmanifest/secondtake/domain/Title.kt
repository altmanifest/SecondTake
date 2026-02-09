package com.altmanifest.secondtake.domain

data class Title(val id: Id, val value: String, val posterUrl: String, val genre: Genre, val rating: Rating) {
    value class Id(val value: String)
}