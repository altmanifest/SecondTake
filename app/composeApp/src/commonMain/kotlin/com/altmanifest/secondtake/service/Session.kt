package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.application.Session
import com.altmanifest.secondtake.domain.ComparisonSchedule
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.domain.Round.Companion.createRound

class Session(comparisonSchedule: ComparisonSchedule, capacity: Round.Capacity): Session {
    private val roundResult = comparisonSchedule.createRound(capacity)
    override val initialSnapshot = roundResult.initialSnapshot
    private val round = roundResult.round

    override fun handle(action: Session.Action): Round.State = when (action) {
        is Session.Action.Rate -> round.rateCurrent(action.preference, action.strength)
        is Session.Action.Skip -> round.skipCurrent()
    }
}

