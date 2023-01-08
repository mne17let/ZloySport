package com.zloysport.data.interactors

import com.zloysport.data.models.Account
import com.zloysport.data.storages.local.LocalStorage

class LoginInteractor(
    private val localStorage: LocalStorage
) {
    fun saveAccount(account: Account) {
        localStorage.saveAccount(account)
    }

    suspend fun getAccountSessionInfo(): AccountSessionInfo {
        val accounts = localStorage.getAllAccounts()

        val result: AccountSessionInfo
        if (accounts.isEmpty()) {
            result = getNoAuthResult()
        } else if (accounts.size > 1) {
            result = getMoreThanOneAccountResult()
        } else {
            result = getSuccessAccountAuthResult()
        }

        return result
    }

    private fun getNoAuthResult(): AccountSessionInfo {
        return AccountSessionInfo(
            result = RESULT_TYPE.NO_AUTH,
            stringResult = NO_AUTH
        )
    }

    private fun getMoreThanOneAccountResult(
    ): AccountSessionInfo {
        return AccountSessionInfo(
            result = RESULT_TYPE.MORE_THAN_ONE_ACCOUNT,
            stringResult = MORE_THAN_ONE_ACCOUNT
        )
    }

    private fun getSuccessAccountAuthResult(
    ): AccountSessionInfo {
        return AccountSessionInfo(
            result = RESULT_TYPE.SUCCESS,
            stringResult = SUCCESS
        )
    }

    data class AccountSessionInfo(
        val result: RESULT_TYPE,
        val stringResult: String = ""
    )

    companion object {
        private const val NO_AUTH = "no_auth"
        private const val MORE_THAN_ONE_ACCOUNT = "more_than_one_account"
        private const val SUCCESS = "success"
    }

    enum class RESULT_TYPE {
        NO_AUTH, MORE_THAN_ONE_ACCOUNT, SUCCESS
    }
}