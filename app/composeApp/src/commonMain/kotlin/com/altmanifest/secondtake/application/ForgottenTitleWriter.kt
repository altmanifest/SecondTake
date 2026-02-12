package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Title

interface ForgottenTitleWriter {
    suspend fun saveAll(titles: Set<Title>)
    suspend fun delete(title: Title)
}