package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Session

interface SessionFactory {
    fun create(setup: Setup = Setup.Default): CreateResult

    sealed class CreateResult {
        data class Success(val session: Session) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }

    sealed class Setup {
        object Default : Setup()
    }
}

