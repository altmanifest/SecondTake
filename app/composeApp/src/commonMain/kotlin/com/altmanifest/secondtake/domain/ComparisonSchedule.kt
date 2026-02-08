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
        fun Set<Title>.scheduleForComparison(config: Comparison.Config, exclude: Set<Pair<Title, Title>> = setOf()): CreateResult {
            if (this.size < 2) {
                return CreateResult.NoTitles
            }

            val titles = this.groupBy { it.genre }.values
                .flatMap { it.sortedBy { title -> title.rating.value } }

            val comparisons = mutableListOf<Comparison>()

            for (i in 0 until titles.size - 1) {
                for (j in i + 1 until titles.size) {
                    val pair = titles[i] to titles[j]
                    if (exclude.containsPair(pair)) {
                        continue
                    }
                    pair.toComparison(config).successOrNull()?.let {
                        comparisons += it
                        break
                    }
                }
            }

            return when {
                comparisons.isEmpty() -> CreateResult.NoComparisons
                else -> CreateResult.Success(ComparisonSchedule(comparisons))
            }
        }

        private fun Iterable<Pair<Title, Title>>.containsPair(pair: Pair<Title, Title>): Boolean =
            (pair.first to pair.second) in this || (pair.second to pair.first) in this
    }

    sealed class CreateResult {
        data class Success(val value: ComparisonSchedule) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }
}
