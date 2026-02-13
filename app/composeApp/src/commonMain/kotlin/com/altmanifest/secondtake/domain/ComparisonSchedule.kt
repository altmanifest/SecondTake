package com.altmanifest.secondtake.domain

import com.altmanifest.secondtake.domain.Comparison.Companion.toComparison

class ComparisonSchedule private constructor(private val comparisons: List<Comparison>) {
    val size = comparisons.size

    fun comparisonAt(position: Int): Comparison = comparisons[position]
    fun takeFirst(n: Int): ComparisonSchedule = when {
        n >= comparisons.size -> this
        else -> ComparisonSchedule(comparisons.subList(0, n))
    }

    companion object {
        fun Set<Title>.scheduleForComparison(config: Comparison.Config, pairingPolicy: (Pair<Title, Title>) -> Boolean = { true }): CreateResult {
            if (this.size < 2) {
                return CreateResult.NoTitles
            }

            val comparisons = this.groupBy { it.genre }.values
                .flatMap { it.sortedBy { title -> title.rating.value } }
                .tryCreateComparisons(config, pairingPolicy)

            return when {
                comparisons.isEmpty() -> CreateResult.NoComparisons
                else -> CreateResult.Success(ComparisonSchedule(comparisons))
            }
        }

        private fun List<Title>.tryCreateComparisons(comparisonConfig: Comparison.Config, pairingPolicy: (Pair<Title, Title>) -> Boolean): List<Comparison> {
            val comparisons = mutableListOf<Comparison>()
            val matchedPairIndices = mutableSetOf<Int>()

            for (firstPairIndex in this.indices) {
                if (firstPairIndex in matchedPairIndices) {
                    continue
                }

                for (secondPairIndex in firstPairIndex + 1 until this.size) {
                    if (secondPairIndex in matchedPairIndices) {
                        continue
                    }

                    val pair = this[firstPairIndex] to this[secondPairIndex]

                    if (!pairingPolicy(pair)) {
                        continue
                    }

                    pair.toComparison(comparisonConfig).successOrNull()?.let { validComparison ->
                        comparisons += validComparison
                        matchedPairIndices.add(firstPairIndex)
                        matchedPairIndices.add(secondPairIndex)
                    }

                    if (firstPairIndex in matchedPairIndices)  {
                        break
                    }
                }
            }
            return comparisons
        }
    }

    sealed class CreateResult {
        data class Success(val value: ComparisonSchedule) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }
}
