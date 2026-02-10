package com.altmanifest.secondtake.domain

class Session(
    initialRound: Round.CreateResult,
    private val nextRound: (titles: Set<Title>, pairingPolicy: (Pair<Title, Title>) -> Boolean) -> Round?
) {

    val initialSnapshot = initialRound.initialSnapshot
    private var round = initialRound.round
    private val skippedPairs = mutableSetOf<Pair<Title, Title>>()
    private val forgotTitle = mutableSetOf<Title>()

    fun handle(action: Action): State {
        val state = when (action) {
            is Action.Rate -> round.rateCurrent(action.preference, action.strength)
            is Action.Forget -> forget(action.choice)
            is Action.Skip -> {
                val comparison = round.snapshot().comparison;
                skippedPairs += comparison.first to comparison.second
                round.skipCurrent()
            }
        }

        return when (state) {
            is Round.State.Running -> State.Running(state.snapshot)
            is Round.State.Finished -> {
                val titlesToRequeue = skippedPairs.flatMap { listOf(it.first, it.second) }.toSet()
                val nextRound = nextRound(titlesToRequeue) { !skippedPairs.containsPair(it) } ?: return State.Finished(
                    ratings = state.ratings,
                    forgotTitles = forgotTitle
                )
                round = nextRound
                skippedPairs.clear()
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

    private fun Iterable<Pair<Title, Title>>.containsPair(pair: Pair<Title, Title>): Boolean =
        (pair.first to pair.second) in this || (pair.second to pair.first) in this

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