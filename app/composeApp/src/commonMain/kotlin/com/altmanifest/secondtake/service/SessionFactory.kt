package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.application.SessionFactory
import com.altmanifest.secondtake.application.SessionFactory.Setup
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.domain.Session
import com.altmanifest.secondtake.domain.Title

class SessionFactory(
    private val titleProvider: TitleProvider,
    private val roundFactory: RoundFactory
) : SessionFactory {
    override fun create(setup: Setup): SessionFactory.CreateResult = when (setup) {
        is Setup.Default -> create { titleProvider.getAll() }
    }

    fun create(provideTitles: () -> Set<Title>): SessionFactory.CreateResult =
        when (val roundResult = roundFactory.create(titles = provideTitles())) {
            is RoundFactory.CreateResult.NoComparisons -> SessionFactory.CreateResult.NoComparisons
            is RoundFactory.CreateResult.NoTitles -> SessionFactory.CreateResult.NoTitles
            is RoundFactory.CreateResult.Success -> SessionFactory.CreateResult.Success(
                Session(
                    initialRound = roundResult.roundResult,
                    nextRound = ::createRoundOrNull
                )
            )
        }

    private fun createRoundOrNull(titles: Set<Title>, exclude: Set<Pair<Title, Title>>): Round? =
        when (val result = roundFactory.create(titles, exclude)) {
            is RoundFactory.CreateResult.Success -> result.roundResult.round
            else -> null
        }
}