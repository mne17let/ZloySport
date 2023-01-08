package com.zloysport.data.storages.local

import com.zloysport.data.models.Account

interface LocalStorage {
    suspend fun getAllAccounts(): List<Account>
    fun saveAccount(account: Account)
}