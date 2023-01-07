package com.zloysport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zloysport.di.ZloySportApplication
import com.zloysport.ui.GlobalState
import com.zloysport.ui.states.LoginStateHolder
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginStateHolder: LoginStateHolder

    @Inject
    lateinit var globalState: GlobalState

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ZloySportApplication).applicationComponent.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        observeGlobalState()
    }

    private fun setUi(hasAcc: Boolean) {
        setContent {
            val navController = rememberNavController()
            SetUpNavHost(navController = navController, hasAcc)
        }
    }

    @Composable
    private fun SetUpNavHost(navController: NavHostController, hasAcc: Boolean) {
        NavHost(navController = navController, startDestination = if (hasAcc) DRILLS else LOGIN) {
            composable(LOGIN) {
                ScreenLogin(loginStateHolder)
            }
            composable(DRILLS) {
                ScreenDrills()
            }
        }
    }

    private fun observeGlobalState() {
        globalState.checkAccount()
        globalState.globalState.observe(this) {
            setUi(it.haveAcc)
        }
    }
}

const val GlobalTag = "GlobalTag"