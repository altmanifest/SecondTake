package com.altmanifest.secondtake.mock

import com.altmanifest.secondtake.application.TitleOwner
import com.altmanifest.secondtake.domain.Genre
import com.altmanifest.secondtake.domain.Title
import com.altmanifest.secondtake.service.TitleProvider.Companion.mockActionMovies

class MockTitleOwner: TitleOwner {
    override fun getAll() : Set<Title> {
        return mockActionMovies.toSet()
    }

    override fun getByGenre(genre: Genre): Set<Title> {
        return mockActionMovies.toSet()
    }

    override fun update(title: Title) {
        println("Simuliere Update in Datenbank für: ${title.value} -> Neues Rating: ${title.rating.value}")
    }

}