package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Session

interface SessionFactory {
    fun create(): CreateResult

    sealed class CreateResult {
        data class Success(val session: Session) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }
}