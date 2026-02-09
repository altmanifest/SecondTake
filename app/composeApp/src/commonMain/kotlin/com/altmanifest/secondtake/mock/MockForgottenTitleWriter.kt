package com.altmanifest.secondtake.mock

import com.altmanifest.secondtake.application.ForgottenTitleWriter
import com.altmanifest.secondtake.domain.Title

class MockForgottenTitleWriter : ForgottenTitleWriter {
    override fun saveAll(titles: Set<Title>) {
        // Simulates saving the forgotten titles
        println("Simuliere Speichern der vergessenen Titel: ${titles.joinToString { it.value }}")
    }

    override fun delete(title: Title) {
        println("Simuliere löschen des vergessenen Titels: ${title}")
    }
}