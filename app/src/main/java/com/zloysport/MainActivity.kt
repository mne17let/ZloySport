package com.zloysport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zloysport.ui.viewmodels.LoginViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SetUpNavHost(navController = navController)
        }
    }

    @Composable
    private fun SetUpNavHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = LOGIN) {
            composable(LOGIN) {
                ScreenLogin(loginViewModel)
            }
        }
    }
}

const val GlobalTag = "GlobalTag"