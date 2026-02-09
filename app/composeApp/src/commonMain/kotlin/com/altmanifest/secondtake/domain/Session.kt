package com.altmanifest.secondtake.domain

class Session(
    initialRound: Round.CreateResult,
    private val nextRound: (titles: Set<Title>, exclude: Set<Pair<Title, Title>>) -> Round?
) {

    val initialSnapshot = initialRound.initialSnapshot
    private var round = initialRound.round
    private val skippedComparisons = mutableSetOf<Comparison.View>()
    private val forgotTitle = mutableSetOf<Title>()

    fun handle(action: Action): State {
        val state = when (action) {
            is Action.Rate -> round.rateCurrent(action.preference, action.strength)
            is Action.Forget -> forget(action.choice)
            is Action.Skip -> {
                skippedComparisons += round.snapshot().comparison
                round.skipCurrent()
            }
        }

        return when (state) {
            is Round.State.Running -> State.Running(state.snapshot)
            is Round.State.Finished -> {
                val pairsToExclude = skippedComparisons.map { it.first to it.second }.toSet()
                val titlesToRequeue = skippedComparisons.flatMap { listOf(it.first, it.second) }.toSet()
                val nextRound = nextRound(titlesToRequeue, pairsToExclude) ?: return State.Finished(
                    ratings = state.ratings,
                    forgotTitles = forgotTitle
                )
                round = nextRound
                skippedComparisons.clear()
                State.Running(round.snapshot())
            }
        }
    }

    private fun forget(choice: Choice): Round.State {
        val view = round.snapshot().comparison
        when (choice) {
            Choice.FIRST -> forgotTitle += view.first
            Choice.SECOND -> forgotTitle += view.second
            Choice.BOTH -> forgotTitle += setOf(view.first, view.second)
        }
        return round.skipCurrent()
    }

    sealed class Action {
        data class Rate(val preference: Preference, val strength: Comparison.Rating.Strength) : Action()
        data class Forget(val choice: Choice) : Action()
        object Skip : Action()
    }

    sealed class State {
        data class Running(val snapshot: Round.Snapshot) : State()
        data class Finished(val ratings: List<Comparison.Rating>, val forgotTitles: Set<Title>) : State()
    }
}