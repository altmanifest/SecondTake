package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Title

interface ForgottenTitleWriter {
    fun saveAll(titles: Set<Title>)
}