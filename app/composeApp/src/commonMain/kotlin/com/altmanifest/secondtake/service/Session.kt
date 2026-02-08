package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.application.Session
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.domain.Title

class Session(initialRound: Round.CreateResult, private val nextRound: (Set<Title>) -> Round?) : Session {

    override val initialSnapshot = initialRound.initialSnapshot
    private var round = initialRound.round
    private val skippedTitles = mutableSetOf<Title>()

    override fun handle(action: Session.Action): Round.State {
        val state = when (action) {
            is Session.Action.Rate -> round.rateCurrent(action.preference, action.strength)
            is Session.Action.Skip -> {
                val view = round.snapshot().comparison
                skippedTitles += setOf(view.first, view.second)
                round.skipCurrent()
            }
        }

        return when (state) {
            is Round.State.Ongoing -> state
            is Round.State.Finished -> {
                val nextRound = nextRound(skippedTitles) ?: return state
                round = nextRound
                Round.State.Ongoing(round.snapshot())
            }
        }
    }
}

