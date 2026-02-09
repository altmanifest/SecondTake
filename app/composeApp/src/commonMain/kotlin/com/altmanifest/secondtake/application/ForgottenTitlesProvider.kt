package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Title

interface ForgottenTitlesProvider {
    fun get(id: Title.Id): Title?
}