package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Title

interface ForgottenTitlesProvider {
    suspend fun get(id: Title.Id): Title?
    suspend fun getAll(): Set<Title>
}