package com.altmanifest.secondtake.di

import com.altmanifest.secondtake.application.ForgottenTitleSource
import com.altmanifest.secondtake.application.GenreAccessor
import com.altmanifest.secondtake.application.ProviderSource
import com.altmanifest.secondtake.application.TitleOwner

interface ProviderContainer {
    val providerSource: ProviderSource
    val genreAccessor: GenreAccessor
    val forgottenTitleSource: ForgottenTitleSource
    val titleOwner: TitleOwner
}