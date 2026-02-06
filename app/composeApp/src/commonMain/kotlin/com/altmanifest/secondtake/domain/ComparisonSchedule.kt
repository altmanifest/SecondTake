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
        fun Set<Title>.scheduleForComparison(config: Comparison.Config): CreateResult {
            if(this.size < 2) {
                return CreateResult.NoTitles
            }

            val comparisons = this.groupBy { it.genre }.values
                .flatMap { it.sortedBy { title -> title.rating.value } }
                .zipWithNext()
                .mapNotNull { it.toComparison(config).successOrNull() }

            return when {
                comparisons.isEmpty() -> CreateResult.NoComparisons
                else -> CreateResult.Success(ComparisonSchedule(comparisons))
            }
        }
    }

    sealed class CreateResult {
        data class Success(val value: ComparisonSchedule) : CreateResult()
        object NoTitles : CreateResult()
        object NoComparisons : CreateResult()
    }
}
