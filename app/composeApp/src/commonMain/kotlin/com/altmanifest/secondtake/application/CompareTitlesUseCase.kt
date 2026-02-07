package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.ComparisonSchedule
import com.altmanifest.secondtake.domain.ComparisonSchedule.Companion.scheduleForComparison
import com.altmanifest.secondtake.domain.Preference
import com.altmanifest.secondtake.domain.Rating
import com.altmanifest.secondtake.domain.Session
import com.altmanifest.secondtake.domain.Session.Companion.createSession
import kotlin.time.Duration

class CompareTitlesUseCase(private val titleProvider: TitleProvider, private val config: Config) {
    fun startSession(): CreateResult =
        when (val schedule = titleProvider.getAll().scheduleForComparison(config.comparisons)) {
            is ComparisonSchedule.CreateResult.NoComparisons -> CreateResult.NoComparisons
            is ComparisonSchedule.CreateResult.NoTitles -> CreateResult.NoTitles
            is ComparisonSchedule.CreateResult.Success -> CreateResult.Success(
                session = ActiveSessionHolder(
                    result = schedule.value.createSession(config.capacity),
                    onFinished = ::applyDecisions
                )
            )
        }

    private fun applyDecisions(decisions: List<Session.Decision>) = decisions.forEach {
        when (it) {
            is Session.Decision.Rated -> applyRatingReduction(it.rating)
        }
    }

    private fun applyRatingReduction(rating: Comparison.Rating) {
        val loserRatingReduction = if (rating.winner.rating.value < rating.loser.rating.value) 2 else 1
        val updatedTitle = rating.loser.copy(
            rating = Rating(
                value = rating.loser.rating.value - loserRatingReduction,
                age = Duration.ZERO
            )
        )
        titleProvider.update(updatedTitle)
    }

    class ActiveSessionHolder(result: Session.CreateResult, private val onFinished: (List<Session.Decision>) -> Unit) {
        var snapshot: Session.Snapshot = result.first
            private set

        private val session = result.session

        fun rateCurrent(preference: Preference, strength: Comparison.Rating.Strength): State =
            when (val state = session.rateCurrent(preference, strength)) {
                is Session.State.Ongoing -> {
                    snapshot = state.snapshot
                    State.Ongoing
                }

                is Session.State.Finished -> {
                    onFinished(state.decision)
                    State.Finished
                }
            }

        sealed class State {
            object Ongoing : State()
            object Finished : State()
        }
    }

    sealed class CreateResult {
        data class Success(val session: ActiveSessionHolder) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }

    data class Config(val comparisons: Comparison.Config, val capacity: Session.Capacity)
}

