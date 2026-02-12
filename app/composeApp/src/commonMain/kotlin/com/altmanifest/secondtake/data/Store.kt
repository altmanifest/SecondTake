package com.altmanifest.secondtake.data

import com.altmanifest.secondtake.domain.Title
import io.github.xxfast.kstore.KStore
import kotlinx.serialization.Serializable

class Store(private val store: KStore<Data>) {
    suspend fun get(): Data {
        val data = store.get()
        if (data == null) {
            store.set(Data())
        }
        return store.get() ?: throw IllegalStateException("Store not initialized")
    }

    suspend fun update(operation: (Data) -> Unit) = store.update {
        val data = it ?: throw IllegalStateException("Store not initialized")
        operation(data)
        data
    }

    @Serializable
    data class Data(
        val forgottenTitles: MutableSet<Title> = mutableSetOf(),
    )
}
