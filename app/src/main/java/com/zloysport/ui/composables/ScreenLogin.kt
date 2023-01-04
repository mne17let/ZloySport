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
            val loginState by remember { viewModel.state }
            val loginValue = loginState.login
            val hasMessage = loginState.hasMessage
            val context = LocalContext.current

            if (hasMessage) {
                val message = loginState.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.onMessageHandled(LoginViewModel.HandledMessage.Toast)

            }

            var passwordValue by remember { mutableStateOf("TextFieldValue - Пароль") }
            TextField(
                value = loginValue,
                onValueChange = {
                    viewModel.onLoginInput(it)
                }
            )

            TextField(
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                }
            )

            Button(onClick = {
                viewModel.onLoginButtonClicked(loginValue, passwordValue)
            }) {
                Text(text = "Войти или зарегистрироваться")
            }
        }
    }
}