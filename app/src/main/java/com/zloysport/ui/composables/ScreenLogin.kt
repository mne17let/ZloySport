package com.zloysport.ui.composables

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

@Composable
fun ScreenLogin(
    stateHolder: LoginStateHolder
) {

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
            val passwordValue = loginState.password
            val message = loginState.message
            val showLoading = loginState.showLoading
            val context = LocalContext.current


            if (message.isNotBlank()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                        stateHolder.onPasswordInput(it)
                    }
                )

                Button(onClick = {
                    stateHolder.onLoginButtonClicked(loginValue, passwordValue)
                }) {
                    Text(text = "?????????? ?????? ????????????????????????????????????")
                }

                Text(
                    modifier = Modifier.padding(SpaceBetweenColumnItems),
                    text = message
                )
            }
        }
    }
}