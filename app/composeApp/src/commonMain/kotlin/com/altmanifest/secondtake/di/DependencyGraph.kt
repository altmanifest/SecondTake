package com.altmanifest.secondtake.di

import androidx.lifecycle.SavedStateHandle
import com.altmanifest.secondtake.application.CompareTitlesUseCase
import com.altmanifest.secondtake.application.DefinedProvider
import com.altmanifest.secondtake.data.Store
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.service.RoundFactory
import com.altmanifest.secondtake.service.SessionFactory
import com.altmanifest.secondtake.ui.viewmodel.ComparisonViewModel
import com.altmanifest.secondtake.ui.viewmodel.ForgottenTitlesViewModel
import com.altmanifest.secondtake.ui.viewmodel.GenreViewModel

class DependencyGraph(private val store: Store) {
    private lateinit var activeContainer: ProviderContainer

    fun selectProvider(provider: DefinedProvider) {
        activeContainer = when (provider) {
            DefinedProvider.Mockify -> MockifyProviderContainer(store)
            else -> TODO("Not implemented yet")
        }
    }

    fun createGenreViewmodel(): GenreViewModel = GenreViewModel(activeContainer.genreAccessor)
    fun createForgottenTitlesViewmodel(): ForgottenTitlesViewModel =
        ForgottenTitlesViewModel(activeContainer.forgottenTitleSource)

    fun createComparisonViewmodel(
        savedStateHandle: SavedStateHandle,
        comparisonConfig: Comparison.Config,
        capacity: Round.Capacity
    ) = ComparisonViewModel(
        useCase = CompareTitlesUseCase(
            sessionFactory = SessionFactory(
                roundFactory = RoundFactory(
                    comparisonConfig = comparisonConfig,
                    capacity = capacity
                )
            ),
            titleOwner = activeContainer.titleOwner,
            forgottenTitleSource = activeContainer.forgottenTitleSource
        ),
        savedStateHandle = savedStateHandle
    )
}