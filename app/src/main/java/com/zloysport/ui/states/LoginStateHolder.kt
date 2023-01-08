package com.zloysport.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.zloysport.data.interactors.LoginInteractor
import com.zloysport.data.models.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginStateHolder(
    private var interactor: LoginInteractor,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : StateHolder {
    var state: MutableState<LoginState> = mutableStateOf(LoginState())

    override fun clear() {
    }

    fun onLoginInput(login: String) {
        state.value = state.value.copy(login = login)
    }

    fun onPasswordInput(password: String) {
        state.value = state.value.copy(password = password)
    }

    fun onLoginButtonClicked(login: String, password: String) {
        state.value = state.value.copy(showLoading = true)

        coroutineScope.launch {
            interactor.saveAccount(Account(login, password))

            val accountSessionInfo = interactor.getAccountSessionInfo()

            delay(2000)

            val result = accountSessionInfo.result
            val message = accountSessionInfo.stringResult

            setLoginState(result, message)
        }
    }

    private fun setLoginState(
        result: LoginInteractor.RESULT_TYPE,
        message: String
    ) {
        when (result) {
            LoginInteractor.RESULT_TYPE.NO_AUTH -> {
                state.value = state.value.copy(noAuth = true, message = message)
            }
            LoginInteractor.RESULT_TYPE.MORE_THAN_ONE_ACCOUNT -> {
                state.value = state.value.copy(moreThanOneAccount = true, message = message)
            }
            LoginInteractor.RESULT_TYPE.SUCCESS -> {
                state.value = state.value.copy(successLogin = true, message = message)
            }
        }
    }

    data class LoginState(
        val login: String = "",
        val password: String = "",
        val message: String = "",
        val showLoading: Boolean = false,
        val successLogin: Boolean = false,
        val noAuth: Boolean = false,
        val moreThanOneAccount: Boolean = false
    )

    enum class HandledMessage {
        SnackBar,
        Toast
    }
}