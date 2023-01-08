package com.zloysport.ui

import androidx.lifecycle.MutableLiveData
import com.zloysport.data.models.Account
import com.zloysport.data.storages.local.LocalStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Singleton

@Singleton
class GlobalState(
    private val localStorage: LocalStorage,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    val globalState = MutableLiveData<GlobalState>()

    fun checkAccount() {
        coroutineScope.launch {
            val accounts = localStorage.getAllAccounts()
            setGlobalState(accounts)
        }
    }

    private suspend fun setGlobalState(accs: List<Account>) {
        withContext(Dispatchers.Main) {
            if (accs.size == 0) {
                globalState.value = GlobalState(
                    AccountData(emptyList()),
                    false
                )
            } else {
                globalState.value = GlobalState(
                    AccountData(accs),
                    true
                )
            }
        }
    }

    class GlobalState(
        val accountData: AccountData,
        val hasAccount: Boolean
    )

    class AccountData(val list: List<Account>)
}