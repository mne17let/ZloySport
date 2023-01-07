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
    private val localStorage: LocalStorage
) {

    val globalState = MutableLiveData<GlobalState>()

    fun checkAccount() {
        CoroutineScope(Dispatchers.IO).launch {
            val accs = localStorage.getAccountData()
            setGlobalState(accs)
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
        val haveAcc: Boolean
    )

    class AccountData(val list: List<Account>)
}