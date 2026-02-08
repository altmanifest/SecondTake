package com.altmanifest.secondtake.domain

class Round private constructor(private val schedule: ComparisonSchedule) {
    private val ratings = mutableListOf<Comparison.Rating>()
    var index = 0

    fun rateCurrent(preference: Preference, ratingStrength: Comparison.Rating.Strength): State {
        if (index >= schedule.size) {
            return State.Finished(ratings.toList())
        }

        val comparison = schedule.comparisonAt(position = index)
        ratings += comparison.rate(preference, ratingStrength)
        index++

        return if (index < schedule.size) {
            State.Ongoing(
                Snapshot(
                    comparison = schedule.comparisonAt(position = index).view(),
                    progress = Progress(
                        current = index + 1,
                        total = schedule.size
                    )
                )
            )
        } else {
            State.Finished(ratings.toList())
        }
    }

    companion object {
        fun ComparisonSchedule.createRound(capacity: Capacity) =
            CreateResult(
                initialSnapshot = Snapshot(
                    comparison = this.comparisonAt(position = 0).view(),
                    progress = Progress(
                        current = 1,
                        total = capacity.value
                    )
                ),
                round = Round(schedule = this.takeFirst(capacity.value))
            )
    }

    sealed class State {
        data class Ongoing(val snapshot: Snapshot) : State()
        data class Finished(val ratings: List<Comparison.Rating>) : State()
    }

    data class Snapshot(val comparison: Comparison.View, val progress: Progress)
    data class Progress(val current: Int, val total: Int)
    data class CreateResult(val initialSnapshot: Snapshot, val round: Round)

    value class Capacity(val value: Int)
}