package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Title

interface TitleUpdater {
    fun update(title: Title)
}