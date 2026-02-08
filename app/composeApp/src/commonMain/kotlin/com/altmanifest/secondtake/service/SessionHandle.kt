package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.application.SessionHandle
import com.altmanifest.secondtake.domain.ComparisonSchedule
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.domain.Round.Companion.createRound

class SessionHandle(comparisonSchedule: ComparisonSchedule, capacity: Round.Capacity): SessionHandle {
    private val roundResult = comparisonSchedule.createRound(capacity)
    override val initialSnapshot = roundResult.initialSnapshot
    private val round = roundResult.round

    override fun handle(action: SessionHandle.Action): Round.State = when (action) {
        is SessionHandle.Action.Rate -> round.rateCurrent(action.preference, action.strength)
        is SessionHandle.Action.Skip -> round.skipCurrent()
    }
}

