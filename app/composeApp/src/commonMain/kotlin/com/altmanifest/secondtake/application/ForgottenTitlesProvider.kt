package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Title

interface ForgottenTitlesProvider {
    fun isForgotten(title: Title): Boolean
}