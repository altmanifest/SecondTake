package com.altmanifest.secondtake

import com.altmanifest.secondtake.data.Store
import io.github.xxfast.kstore.KStore

expect fun createStore(): KStore<Store.Data>