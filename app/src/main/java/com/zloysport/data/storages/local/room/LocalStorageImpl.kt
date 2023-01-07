package com.zloysport.data.storages.local.room

import com.zloysport.data.models.Account
import com.zloysport.data.storages.local.LocalStorage

class LocalStorageImpl(
    private val dao: AccountsDao
) : LocalStorage {
    override fun getAccountData(): List<Account> {
        val accs = dao.getAllAccounts()
        return accs
    }

    override fun saveAccount(account: Account) {
        dao.addAccount(account)
    }
}