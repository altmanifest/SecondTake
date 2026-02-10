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
                .createPairs(pairingPolicy)
                .mapNotNull { it.toComparison(config).successOrNull() }

            return when {
                comparisons.isEmpty() -> CreateResult.NoComparisons
                else -> CreateResult.Success(ComparisonSchedule(comparisons))
            }
        }

        private fun List<Title>.createPairs(pairingPolicy: (Pair<Title, Title>) -> Boolean): List<Pair<Title, Title>> =
            this.windowed(size = 2, step = 2, partialWindows = false) {
                val pair = it[0] to it[1]
                if (pairingPolicy(pair)) pair else null
            }.filterNotNull()
    }

    sealed class CreateResult {
        data class Success(val value: ComparisonSchedule) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }
}
