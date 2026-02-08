package com.altmanifest.secondtake.application

interface SessionFactory {
    fun create(): CreateResult

    sealed class CreateResult {
        data class Success(val session: Session) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }
}