package com.zloysport.ui.viewmodels

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

class LoginViewModel : ViewModel() {
    var count = 0

    var state: MutableState<LoginState?> = mutableStateOf(null)

    fun checkAndlogin(login: String, password: String) {
        validateAndSetState(login, password)
        count++
    }

    fun onStateConsumed(consumedState: LoginState) {
        when(consumedState) {
            is LoginState.Fail -> setAwaitInputState()
            is LoginState.Success -> setAwaitInputState()
            is LoginState.AwaitInput -> {}
        }
    }

    private fun validateAndSetState(login: String, password: String) {
        val check = Random.nextInt(0, 2)

        if (check == 0) {
            setFailState(login, password)
        } else {
            setSuccessState(login, password, check)
        }
    }

    private fun setSuccessState(login: String, password: String, check: Int) {
        state.value =
            LoginState.Success("$count ЧИСЛО == $check Успешный вход = $login || $password")
    }

    private fun setFailState(login: String, password: String) {
        state.value = LoginState.Fail("$count Ошибка входа $login || $password")
    }

    private fun setAwaitInputState() {
        state.value = LoginState.AwaitInput()
    }

    @Parcelize
    sealed class LoginState : Parcelable {
        class Success(val message: String) : LoginState()
        class Fail(val message: String) : LoginState()
        class AwaitInput : LoginState()
    }
}