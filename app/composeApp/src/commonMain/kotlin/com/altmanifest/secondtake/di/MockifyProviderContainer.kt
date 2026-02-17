package com.altmanifest.secondtake.di

import com.altmanifest.secondtake.application.ForgottenTitleSource
import com.altmanifest.secondtake.application.GenreAccessor
import com.altmanifest.secondtake.application.ProviderSource
import com.altmanifest.secondtake.application.TitleOwner
import com.altmanifest.secondtake.data.ForgottenTitlesSource
import com.altmanifest.secondtake.data.Store
import com.altmanifest.secondtake.mock.MockTitleOwner

class MockifyProviderContainer(private val store: Store): ProviderContainer {
    private val mockTitleOwner = MockTitleOwner()
    override val providerSource: ProviderSource
        get() = com.altmanifest.secondtake.data.ProviderSource(store)
    override val genreAccessor: GenreAccessor
        get() = mockTitleOwner
    override val forgottenTitleSource: ForgottenTitleSource
        get() = ForgottenTitlesSource(store)
    override val titleOwner: TitleOwner
        get() = mockTitleOwner
}