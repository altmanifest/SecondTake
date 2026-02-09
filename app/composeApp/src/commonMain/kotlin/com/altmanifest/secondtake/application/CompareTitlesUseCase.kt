package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Rating
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.domain.Session
import com.altmanifest.secondtake.domain.Title
import kotlin.time.Duration

class CompareTitlesUseCase(
    private val sessionFactory: SessionFactory,
    private val titleUpdater: TitleUpdater,
    private val forgottenTitleWriter: ForgottenTitleWriter) {
    private lateinit var session: Session

    fun start(setup: SessionFactory.Setup = SessionFactory.Setup.Default): CreateResult =
        when (val result = sessionFactory.create(setup)) {
            is SessionFactory.CreateResult.NoComparisons -> CreateResult.NoComparisons
            is SessionFactory.CreateResult.NoTitles -> CreateResult.NoTitles
            is SessionFactory.CreateResult.Success -> {
                this.session = result.session
                CreateResult.Success(result.session.initialSnapshot)
            }
        }

    fun handle(action: Session.Action): State = when (val res = session.handle(action)) {
        is Session.State.Running -> State.Running(res.snapshot)
        is Session.State.Finished -> {
            res.ratings.forEach(::applyRatingReduction)
            forgottenTitleWriter.saveAll(res.forgotTitles)
            State.Finished
        }
    }

    private fun applyRatingReduction(rating: Comparison.Rating) {
        val loserRatingReduction = if (rating.winner.rating.value < rating.loser.rating.value) -2 else -1
        updateTitle(rating.loser, ratingIncrease = loserRatingReduction)
        updateTitle(rating.winner, ratingIncrease = when (rating.strength) {
            Comparison.Rating.Strength.LOW -> 0
            Comparison.Rating.Strength.MEDIUM -> 1
            Comparison.Rating.Strength.HIGH -> 2
        })
    }

    private fun updateTitle(title: Title, ratingIncrease: Int) {
        val updatedTitle = title.copy(
            rating = Rating(
                value = title.rating.value + ratingIncrease,
                age = Duration.ZERO
            )
        )
        titleUpdater.update(updatedTitle)
    }

    sealed class CreateResult {
        data class Success(val initialSnapshot: Round.Snapshot) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }

    sealed class State {
        data class Running(val snapshot: Round.Snapshot) : State()
        object Finished : State()
    }

}

