package com.zloysport.data.storages

import com.zloysport.data.models.Account

interface RemoteStorage {
    fun getAccountData(): Account
}