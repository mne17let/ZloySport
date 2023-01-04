package com.zloysport.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class LoginViewModel : ViewModel() {
    var count = 0

    var state: MutableState<LoginState> = mutableStateOf(LoginState())

    fun onLoginInput(login: String) {
        state.value = LoginState(
            login = login
        )
    }

    fun onLoginButtonClicked(login: String, password: String) {
        validateAndSetState(login, password)
    }

    fun onMessageHandled(handledMessage: HandledMessage) {
        val oldValue = state.value
        state.value = LoginState(
            login = oldValue.login,
            password = oldValue.password
        )
    }

    private fun validateAndSetState(login: String, password: String) {
        val check = Random.nextInt(0, 2)

        if (check == 0) {
            setSuccessLoginState(login, password)
        } else {
            setErrorState(login, password, check)
        }
        count++
    }

    private fun setSuccessLoginState(
        login: String,
        password: String
    ) {
        state.value =
            LoginState(
                login = login,
                password = password,
                hasMessage = true,
                message = "$count Ошибка входа $login || $password",
                hasError = true
            )
    }

    private fun setErrorState(login: String, password: String, check: Int) {
        state.value =
            LoginState(
                login = login,
                password = password,
                hasMessage = true,
                message = "$count ЧИСЛО == $check Успешный вход = $login || $password",
                hasError = false
            )
    }

    class LoginState(
        val message: String = "Дефолтное",
        val hasMessage: Boolean = false,
        val login: String = "ДД",
        val password: String = "",
        val hasError: Boolean = false
    )

    enum class HandledMessage {
        SnackBar,
        Toast
    }
}