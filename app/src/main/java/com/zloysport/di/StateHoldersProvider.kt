package com.zloysport.di

import com.zloysport.ui.states.StateHolder

object StateHoldersProvider {
    private val mapOfStateHolders = mutableMapOf<String, StateHolder>()

    @Suppress("UNCHECKED_CAST")
    fun <T> getOrCreateStateHolder(key: String, creator: () -> T): T {
        val currentInstance = mapOfStateHolders[key]
        if (currentInstance == null) {
            val created = creator()
            mapOfStateHolders[key] = created as StateHolder
            return created
        }
        return mapOfStateHolders[key] as T
    }

    fun clearStateHolder(key: String) {
        mapOfStateHolders.remove(key)
    }

    const val LOGIN = "login"
}