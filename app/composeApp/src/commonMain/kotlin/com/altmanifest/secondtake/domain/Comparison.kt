package com.altmanifest.secondtake.domain

import kotlin.reflect.KClass
import kotlin.time.Duration

class Comparison private constructor(private val first: Title, private val second: Title) {
    fun rate(preference: Preference, ratingStrength: Rating.Strength): Rating = when (preference) {
        Preference.FIRST -> Rating(winner = first, loser = second, strength = ratingStrength)
        Preference.SECOND -> Rating(winner = second, loser = first, strength = ratingStrength)
    }

    fun view() = View(first, second, first.genre)

    companion object {
        fun Pair<Title, Title>.toComparison(config: Config): CreateResult {
            if (first::class != second::class) {
                return CreateResult.DifferentTitleTypes(first::class, second::class)
            }
            if (first == second) {
                return CreateResult.SameTitle(first)
            }
            if (first.genre != second.genre) {
                return CreateResult.DifferentGenre(first.genre, second.genre)
            }

            val tooRecentRatings = setOf(first, second).filter { it.rating.age < config.minRatingAge }.map {
                RatingAgeDifference(
                    title = it,
                    missingAge = config.minRatingAge - it.rating.age,
                )
            }.toSet()
            if (tooRecentRatings.isNotEmpty()) {
                return CreateResult.RatingTooRecent(differences = tooRecentRatings)
            }

            if (first.rating.notComparableWith(second.rating, tolerance = config.maxPointDifference)) {
                return CreateResult.PointDifferenceTooHigh(
                    expected = config.maxPointDifference,
                    got = first.rating.pointDifference(second.rating)
                )
            }

            return CreateResult.Success(Comparison(first, second))
        }
    }

    sealed class CreateResult {
        data class Success(val comparison: Comparison) : CreateResult()
        data class DifferentTitleTypes(val first: KClass<out Title>, val second: KClass<out Title>) : CreateResult()
        data class SameTitle(val title: Title) : CreateResult()
        data class DifferentGenre(val first: Genre, val second: Genre) : CreateResult()
        data class PointDifferenceTooHigh(val expected: Double, val got: Double) : CreateResult()
        data class RatingTooRecent(val differences: Set<RatingAgeDifference>) : CreateResult()

        fun successOrNull(): Comparison? = if (this is Success) comparison else null
    }

    data class RatingAgeDifference(val title: Title, val missingAge: Duration)

    data class Config(
        val maxPointDifference: Double,
        val minRatingAge: Duration
    )

    data class Rating(val winner: Title, val loser: Title, val strength: Strength) {
        enum class Strength {
            LOW, MEDIUM, HIGH
        }
    }

    data class View(val first: Title, val second: Title, val genre: Genre)
}
