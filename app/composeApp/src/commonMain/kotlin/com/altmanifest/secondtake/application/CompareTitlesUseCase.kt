package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Rating
import com.altmanifest.secondtake.domain.Round
import kotlin.time.Duration

class CompareTitlesUseCase(private val sessionFactory: SessionFactory, private val titleUpdater: TitleUpdater) {
    private lateinit var session: Session

    fun start(): CreateResult =
        when (val result = sessionFactory.create()) {
            is SessionFactory.CreateResult.NoComparisons -> CreateResult.NoComparisons
            is SessionFactory.CreateResult.NoTitles -> CreateResult.NoTitles
            is SessionFactory.CreateResult.Success -> {
                this.session = result.session
                CreateResult.Success(result.session.initialSnapshot)
            }
        }

    fun handle(action: Session.Action): State = when (val res = session.handle(action)) {
        is Round.State.Ongoing -> State.Running(res.snapshot)
        is Round.State.Finished -> {
            res.ratings.forEach(::applyRatingReduction)
            State.Finished
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
        titleUpdater.update(updatedTitle)
    }

    sealed class CreateResult {
        class Success(initialSnapshot: Round.Snapshot) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }

    sealed class State {
        data class Running(val snapshot: Round.Snapshot) : State()
        object Finished : State()
    }

}

