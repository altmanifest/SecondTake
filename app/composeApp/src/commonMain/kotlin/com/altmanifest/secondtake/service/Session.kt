package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.application.Session
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.domain.Title

class Session(
    initialRound: Round.CreateResult,
    private val nextRound: (titles: Set<Title>, exclude: Set<Pair<Title, Title>>) -> Round?
) : Session {

    override val initialSnapshot = initialRound.initialSnapshot
    private var round = initialRound.round
    private val skippedComparisons = mutableSetOf<Comparison.View>()

    override fun handle(action: Session.Action): Round.State {
        val state = when (action) {
            is Session.Action.Rate -> round.rateCurrent(action.preference, action.strength)
            is Session.Action.Skip -> {
                skippedComparisons += round.snapshot().comparison
                round.skipCurrent()
            }
        }

        return when (state) {
            is Round.State.Running -> state
            is Round.State.Finished -> {
                val pairsToExclude = skippedComparisons.map { it.first to it.second }.toSet()
                val titlesToRequeue = skippedComparisons.flatMap { listOf(it.first, it.second) }.toSet()
                val nextRound = nextRound(titlesToRequeue, pairsToExclude) ?: return state
                round = nextRound
                Round.State.Running(round.snapshot())
            }
        }
    }
}

