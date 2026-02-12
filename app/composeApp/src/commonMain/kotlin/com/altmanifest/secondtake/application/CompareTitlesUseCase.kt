package com.altmanifest.secondtake.application

import com.altmanifest.secondtake.domain.Comparison
import com.altmanifest.secondtake.domain.Genre
import com.altmanifest.secondtake.domain.Rating
import com.altmanifest.secondtake.domain.Round
import com.altmanifest.secondtake.domain.Session
import com.altmanifest.secondtake.domain.Title
import kotlin.time.Duration

class CompareTitlesUseCase(
    private val sessionFactory: SessionFactory,
    private val titleOwner: TitleOwner,
    private val forgottenTitleSource: ForgottenTitleSource
) {
    private lateinit var session: Session

    suspend fun start(setup: Setup = Setup.Default): CreateResult {
        val titles = when (setup) {
            is Setup.Default -> titleOwner.getAll()
            is Setup.ByGenre -> titleOwner.getByGenre(setup.genre)
        }

        titles.deleteOutdatedForgottenTitles()

        val ratedTitles = titles.filterOutForgotten().toSet()
        return when (val result = sessionFactory.create(ratedTitles)) {
            is SessionFactory.CreateResult.NoComparisons -> CreateResult.NoComparisons
            is SessionFactory.CreateResult.NoTitles -> CreateResult.NoTitles
            is SessionFactory.CreateResult.Success -> {
                this.session = result.session
                CreateResult.Success(result.session.initialSnapshot)
            }
        }
    }

    private suspend fun Set<Title>.filterOutForgotten() = this.filter { forgottenTitleSource.get(it.id) == null }

    suspend fun handle(action: Session.Action): State = when (val res = session.handle(action)) {
        is Session.State.Running -> State.Running(res.snapshot)
        is Session.State.Finished -> {
            res.ratings.forEach(::applyRatingReduction)
            forgottenTitleSource.saveAll(res.forgotTitles)
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

    private suspend fun Set<Title>.deleteOutdatedForgottenTitles() = this.forEach { title ->
        val forgottenTitle = forgottenTitleSource.get(title.id) ?: return@forEach
        if (forgottenTitle.rating.age > title.rating.age) {
            forgottenTitleSource.delete(title)
        }
    }

    private fun updateTitle(title: Title, ratingIncrease: Int) {
        val updatedTitle = title.copy(
            rating = Rating(
                value = title.rating.value + ratingIncrease,
                age = Duration.ZERO
            )
        )
        titleOwner.update(updatedTitle)
    }

    sealed class Setup {
        object Default : Setup()
        data class ByGenre(val genre: Genre) : Setup()
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

