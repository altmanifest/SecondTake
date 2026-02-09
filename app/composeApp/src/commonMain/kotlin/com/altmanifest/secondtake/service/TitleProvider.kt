package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.application.TitleProvider
import com.altmanifest.secondtake.domain.Genre
import com.altmanifest.secondtake.domain.Title

class TitleProvider : TitleProvider {
    override fun getAll() : Set<Title> {
        TODO()
    }

    override fun getByGenre(genre: Genre) : Set<Title> {
        TODO()
    }
}