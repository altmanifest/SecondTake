package com.altmanifest.secondtake.domain

class Round private constructor(private val schedule: ComparisonSchedule) {
    private val ratings = mutableListOf<Comparison.Rating>()
    private var index = 0

    fun snapshot() : Snapshot = Snapshot(
        comparison = schedule.comparisonAt(index).view(),
        progress = Progress(
            current = index + 1,
            total = schedule.size
        )
    )

    fun rateCurrent(preference: Preference, ratingStrength: Comparison.Rating.Strength): State = advance {
        val comparison = schedule.comparisonAt(position = index)
        ratings += comparison.rate(preference, ratingStrength)
    }

    fun skipCurrent(): State = advance { }

    private fun advance(action: () -> Unit): State {
        action()
        index++

        return if (index < schedule.size) State.Running(snapshot()) else {
            State.Finished(ratings.toList())
        }
    }

    companion object {
        fun ComparisonSchedule.createRound(capacity: Capacity): CreateResult {
            val round = Round(schedule = this.takeFirst(capacity.value))
            return CreateResult(
                initialSnapshot = round.snapshot(),
                round = round
            )
        }
    }

    sealed class State {
        data class Running(val snapshot: Snapshot) : State()
        data class Finished(val ratings: List<Comparison.Rating>) : State()
    }

    data class Snapshot(val comparison: Comparison.View, val progress: Progress)
    data class Progress(val current: Int, val total: Int)
    data class CreateResult(val initialSnapshot: Snapshot, val round: Round)

    value class Capacity(val value: Int)
}