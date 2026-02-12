package com.altmanifest.secondtake

import com.altmanifest.secondtake.data.Store
import io.github.xxfast.kstore.storage.storeOf

actual fun createStore() = storeOf<Store.Data>("secondtake")
