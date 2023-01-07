package com.zloysport.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.zloysport.data.interactors.LoginInteractor
import com.zloysport.data.models.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoginStateHolder(
    private var interactor: LoginInteractor
) : StateHolder {
    var count = 0

    var state: MutableState<LoginState> = mutableStateOf(LoginState())

    override fun clear() {
    }

    fun onLoginInput(login: String) {
        state.value = LoginState(
            login = login
        )
    }

    fun onLoginButtonClicked(login: String, password: String) {
        val oldState = state.value
        state.value = LoginState(
            message = oldState.message,
            login = oldState.login,
            password = oldState.password,
            hasMessage = oldState.hasMessage,
            hasError = oldState.hasError,
            showLoading = true
        )

        CoroutineScope(Dispatchers.IO).launch {
            interactor.saveAccount(Account(login, password))

            val accs = interactor.getAccounts()
            val msg = accs.toString()
            delay(2000)

            validateAndSetState(msg, login, password)

            val oldestState = state.value
            state.value = LoginState(
                message = oldestState.message,
                login = oldestState.login,
                password = oldestState.password,
                hasMessage = oldestState.hasMessage,
                hasError = oldestState.hasError,
                showLoading = false
            )
        }
    }

    fun onMessageHandled(handledMessage: HandledMessage) {
        val oldState = state.value
        state.value = LoginState(
            message = oldState.message,
            login = oldState.login,
            password = oldState.password
        )
    }

    private fun validateAndSetState(accountsMessage: String, login: String, password: String) {
        val check = Random.nextInt(0, 2)

        if (check == 0) {
            setSuccessState(accountsMessage, login, password)
        } else {
            setErrorState(accountsMessage, login, password, check)
        }
        count++
    }

    private fun setErrorState(
        accountsMessage: String,
        login: String,
        password: String,
        check: Int
    ) {
        state.value =
            LoginState(
                login = login,
                password = password,
                hasMessage = true,
                message = "$count ЧИСЛО == $check || Ошибка входа $login || $password || аккаунты == $accountsMessage",
                hasError = true
            )
    }

    private fun setSuccessState(
        accountsMessage: String,
        login: String,
        password: String,
    ) {
        state.value =
            LoginState(
                login = login,
                password = password,
                hasMessage = true,
                message = "$count Успешный вход = $login || $password || аккаунты == $accountsMessage",
                hasError = false
            )
    }

    class LoginState(
        val message: String = "Дефолтное",
        val hasMessage: Boolean = false,
        val login: String = "ДД",
        val password: String = "",
        val hasError: Boolean = false,
        val showLoading: Boolean = false
    )

    enum class HandledMessage {
        SnackBar,
        Toast
    }
}