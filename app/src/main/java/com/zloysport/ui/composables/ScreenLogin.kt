package com.zloysport

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.zloysport.ui.theme.SpaceBetweenColumnItems
import com.zloysport.ui.viewmodels.LoginViewModel
import kotlin.math.log

var count = 0

@Composable
fun ScreenLogin(
    viewModel: LoginViewModel
) {
    Log.d(GlobalTag, "$count рекомпозиция")
    count++

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(SpaceBetweenColumnItems),
        ) {
            val loginState by rememberSaveable { viewModel.state }
            val context = LocalContext.current

            LaunchedEffect(key1 = loginState) {
                when (loginState) {
                    is LoginViewModel.LoginState.Success -> {
                        val state = loginState as LoginViewModel.LoginState.Success
                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        viewModel.onStateConsumed(state)
                    }
                    is LoginViewModel.LoginState.Fail -> {
                        val state = loginState as LoginViewModel.LoginState.Fail
                        viewModel.onStateConsumed(state)

                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }


            var loginValue by remember { mutableStateOf("TextFieldValue - Логин") }
            var passwordValue by remember { mutableStateOf("TextFieldValue - Пароль") }
            TextField(
                value = loginValue,
                onValueChange = {
                    loginValue = it
                }
            )

            TextField(
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                }
            )

            Button(onClick = {
                viewModel.checkAndlogin(loginValue, passwordValue)
            }) {
                Text(text = "Войти или зарегистрироваться")
            }
        }
    }
}