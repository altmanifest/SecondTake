package com.altmanifest.secondtake.service

import com.altmanifest.secondtake.application.SessionFactory
import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.ComparisonSchedule
import com.altmanifest.secondtake.domain.ComparisonSchedule.Companion.scheduleForComparison
import com.altmanifest.secondtake.domain.Round

class SessionFactory(private val titleProvider: TitleProvider, private val config: Config) : SessionFactory {
    override fun create(): SessionFactory.CreateResult =
        when (val schedule = titleProvider.getAll().scheduleForComparison(config.comparisons)) {
            is ComparisonSchedule.CreateResult.NoComparisons -> SessionFactory.CreateResult.NoComparisons
            is ComparisonSchedule.CreateResult.NoTitles -> SessionFactory.CreateResult.NoTitles
            is ComparisonSchedule.CreateResult.Success -> SessionFactory.CreateResult.Success(
                SessionHandle(schedule.value, config.capacity)
            )
        }

    data class Config(val comparisons: Comparison.Config, val capacity: Round.Capacity)
}