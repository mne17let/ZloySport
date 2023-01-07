package com.zloysport

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.zloysport.ui.theme.SpaceBetweenColumnItems
import com.zloysport.ui.states.LoginStateHolder

var count = 0

@Composable
fun ScreenLogin(
    stateHolder: LoginStateHolder
) {

    Log.d(GlobalTag, "$count рекомпозиция")
    count++

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(SpaceBetweenColumnItems),
            verticalArrangement = Arrangement.spacedBy(SpaceBetweenColumnItems),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val loginState by remember { stateHolder.state }
            val loginValue = loginState.login
            val hasMessage = loginState.hasMessage
            val accsMessage = loginState.message
            val showLoading = loginState.showLoading
            val context = LocalContext.current

            var passwordValue by remember { mutableStateOf("TextFieldValue - Пароль") }

            if (hasMessage) {
                val message = loginState.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                stateHolder.onMessageHandled(LoginStateHolder.HandledMessage.Toast)

            }

            if (showLoading) {
                CircularProgressIndicator()
            } else {
                TextField(
                    value = loginValue,
                    onValueChange = {
                        stateHolder.onLoginInput(it)
                    }
                )

                TextField(
                    value = passwordValue,
                    onValueChange = {
                        passwordValue = it
                    }
                )

                Button(onClick = {
                    stateHolder.onLoginButtonClicked(loginValue, passwordValue)
                }) {
                    Text(text = "Войти или зарегистрироваться")
                }

                Text(
                    modifier = Modifier.padding(SpaceBetweenColumnItems),
                    text = accsMessage
                )
            }
        }
    }
}