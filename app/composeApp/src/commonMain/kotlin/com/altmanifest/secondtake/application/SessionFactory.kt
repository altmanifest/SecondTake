package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Session
import com.altmanifest.secondtake.domain.Title

interface SessionFactory {
    fun create(titles: Set<Title>): CreateResult

    sealed class CreateResult {
        data class Success(val session: Session) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }
}

