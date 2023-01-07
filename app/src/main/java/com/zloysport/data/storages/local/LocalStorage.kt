package com.zloysport.data.storages.local

import com.zloysport.data.models.Account

interface LocalStorage {
    fun getAccountData(): List<Account>
    fun saveAccount(account: Account)
}