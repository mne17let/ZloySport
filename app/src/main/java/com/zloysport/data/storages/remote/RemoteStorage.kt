package com.zloysport.data.storages.remote

import com.zloysport.data.models.Account

interface RemoteStorage {
    fun getAccountData(): Account
}