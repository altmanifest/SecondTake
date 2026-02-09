package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Genre
import com.altmanifest.secondtake.domain.Title

interface TitleProvider {
    fun getAll() : Set<Title>
    fun getByGenre(genre: Genre) : Set<Title>
}