package com.zloysport.data.storages

import com.zloysport.data.models.Account

interface LocalStorage {
    fun getAccountData(): Account
}