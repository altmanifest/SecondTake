package com.altmanifest.secondtake.domain

class Session private constructor(private val schedule: ComparisonSchedule) {
    private val decisions = mutableListOf<Decision>()
    var index = 0

    fun rateCurrent(preference: Preference, ratingStrength: Comparison.Rating.Strength): State {
        if (index >= schedule.size) {
            return State.Finished(decisions.toList())
        }

        val comparison = schedule.comparisonAt(position = index)
        decisions += Decision.Rated(rating = comparison.rate(preference, ratingStrength))
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
            State.Finished(decisions.toList())
        }
    }

    companion object {
        fun ComparisonSchedule.createSession(capacity: Capacity) =
            CreateResult(
                first = Snapshot(
                    comparison = this.comparisonAt(position = 0).view(),
                    progress = Progress(
                        current = 1,
                        total = capacity.value
                    )
                ),
                session = Session(schedule = this.takeFirst(capacity.value))
            )
    }


    sealed class Decision {
        data class Rated(val rating: Comparison.Rating) : Decision()
    }

    sealed class State {
        data class Ongoing(val snapshot: Snapshot) : State()
        data class Finished(val decision: List<Decision>) : State()
    }

    data class Snapshot(val comparison: Comparison.View, val progress: Progress)
    data class Progress(val current: Int, val total: Int)
    data class CreateResult(val first: Snapshot, val session: Session)

    value class Capacity(val value: Int)
}