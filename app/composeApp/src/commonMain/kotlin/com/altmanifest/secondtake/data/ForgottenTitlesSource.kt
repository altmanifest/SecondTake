package com.altmanifest.secondtake.data

import com.altmanifest.secondtake.application.ForgottenTitleSource
import com.altmanifest.secondtake.domain.Title
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toSet

class ForgottenTitlesSource(private val store: Store) : ForgottenTitleSource {
    override suspend fun get(id: Title.Id): Title? =
        store.get().forgottenTitles.find { it.id == id }

    override suspend fun getAll(): Set<Title> = store.get().forgottenTitles.toSet()

    override suspend fun saveAll(titles: Set<Title>) {
        val existingIds = titles.asFlow()
            .mapNotNull { t -> get(t.id)?.id }
            .toSet()

        store.update { state ->
            state.forgottenTitles.removeAll { it.id in existingIds }
            state.forgottenTitles.addAll(titles)
        }
    }

    override suspend fun delete(title: Title) =
        store.update { it.forgottenTitles.remove(title) }
}
