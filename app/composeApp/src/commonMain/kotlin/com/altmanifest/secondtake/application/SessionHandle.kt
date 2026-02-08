package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Preference
import com.altmanifest.secondtake.domain.Round

interface SessionHandle {
    val initialSnapshot: Round.Snapshot
    fun handle(action: Action): Round.State

    sealed class Action {
        data class Rate(val preference: Preference, val strength: Comparison.Rating.Strength) : Action()
    }
}