package com.altmanifest.secondtake.mock

import com.altmanifest.secondtake.application.TitleUpdater
import com.altmanifest.secondtake.domain.Title

class MockTitleUpdater : TitleUpdater {
    override fun update(title: Title) {
        println("Simuliere Update in Datenbank für: ${title.value} -> Neues Rating: ${title.rating.value}")
    }
}