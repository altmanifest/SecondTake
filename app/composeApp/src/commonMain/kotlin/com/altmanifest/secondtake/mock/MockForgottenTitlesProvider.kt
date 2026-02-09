package com.altmanifest.secondtake.mock

import com.altmanifest.secondtake.application.ForgottenTitlesProvider
import com.altmanifest.secondtake.domain.Title

class MockForgottenTitlesProvider : ForgottenTitlesProvider {
    // Check if the title is on the forgotten list
    override fun get(id: Title.Id): Title? {
        return null
    }
}