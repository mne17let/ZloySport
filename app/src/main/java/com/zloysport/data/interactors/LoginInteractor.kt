package com.zloysport.data.interactors

import com.zloysport.data.models.Account
import com.zloysport.data.storages.local.LocalStorage

class LoginInteractor(
    private val localStorage: LocalStorage
) {
    fun saveAccount(account: Account) {
        localStorage.saveAccount(account)
    }

    fun getAccounts(): List<Account> {
        return localStorage.getAccountData()
    }
}