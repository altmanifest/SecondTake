package com.altmanifest.secondtake.mock

import com.altmanifest.secondtake.application.ForgottenTitleSource
import com.altmanifest.secondtake.domain.Title

class MockForgottenTitleSource: ForgottenTitleSource {
    private val forgottenTitles = MockTitleOwner.mockActionMovies.take(6).toMutableList()

    override fun get(id: Title.Id): Title? {
        return forgottenTitles.find { it.id == id }
    }

    override fun saveAll(titles: Set<Title>) {
        titles.forEach { title ->
            if (forgottenTitles.none { it.id == title.id }) {
                forgottenTitles.add(title)
            }
        }
    }

    override fun delete(title: Title) {
        forgottenTitles.removeAll { it.id == title.id }
    }
}