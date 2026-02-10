package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.ComparisonSchedule
import com.altmanifest.secondtake.domain.ComparisonSchedule.Companion.scheduleForComparison
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.domain.Round.Companion.createRound
import com.altmanifest.secondtake.domain.Title

class RoundFactory(private val comparisonConfig: Comparison.Config, private val capacity: Round.Capacity) {

    fun create(titles: Set<Title>, pairingPolicy: (Pair<Title, Title>) -> Boolean = { true }): CreateResult =
        when (val schedule = titles.scheduleForComparison(comparisonConfig, pairingPolicy)) {
            is ComparisonSchedule.CreateResult.NoTitles -> CreateResult.NoTitles
            is ComparisonSchedule.CreateResult.NoComparisons ->
                CreateResult.NoComparisons

            is ComparisonSchedule.CreateResult.Success ->
                CreateResult.Success(schedule.value.createRound(capacity))
        }

    sealed class CreateResult {
        data class Success(val roundResult: Round.CreateResult) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }
}

