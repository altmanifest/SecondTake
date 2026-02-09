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
            val usedIndices = mutableSetOf<Int>() // Merkt sich, welche Titel schon verplant sind

            // 2. Iteriere durch alle Titel
            for (i in titles.indices) {
                if (i in usedIndices) continue // Titel i ist schon in einem Vergleich -> überspringen

                // Suche einen Partner j für i
                for (j in i + 1 until titles.size) {
                    if (j in usedIndices) continue // Titel j ist schon vergeben -> überspringen

                    val pair = titles[i] to titles[j]

                    // Prüfen, ob das Paar ausgeschlossen ist (z.B. weil es übersprungen wurde)
                    if (exclude.containsPair(pair)) continue

                    // Versuch, den Vergleich zu erstellen (prüft Rating-Differenz etc.)
                    pair.toComparison(config).successOrNull()?.let { validComparison ->
                        comparisons += validComparison

                        // Beide Titel als "benutzt" markieren
                        usedIndices.add(i)
                        usedIndices.add(j)
                    }

                    // Wenn wir einen Partner für i gefunden haben, brechen wir die innere Suche ab
                    if (i in usedIndices) break
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
