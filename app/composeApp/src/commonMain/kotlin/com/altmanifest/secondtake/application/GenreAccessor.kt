package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Genre

interface GenreAccessor {
    fun getAvailableGenres(): List<Genre>
}