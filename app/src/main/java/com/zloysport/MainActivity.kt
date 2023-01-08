package com.zloysport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.zloysport.di.ZloySportApplication
import com.zloysport.ui.GlobalState
import com.zloysport.ui.states.LoginStateHolder
import com.zloysport.ui.states.AllDrillsStateHolder
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginStateHolder: LoginStateHolder

    @Inject
    lateinit var allDrillsStateHolder: AllDrillsStateHolder

    @Inject
    lateinit var globalState: GlobalState

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ZloySportApplication).applicationComponent.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        observeGlobalState()
    }

    private fun setComposableUi(hasAccount: Boolean) {
        setContent {
            val navController = rememberNavController()
            SetUpNavHost(
                navController = navController,
                hasAccount,
                loginStateHolder,
                allDrillsStateHolder
            )
        }
    }

    private fun observeGlobalState() {
        globalState.checkAccount()
        globalState.globalState.observe(this) {
            setComposableUi(it.hasAccount)
        }
    }
}

const val GlobalTag = "GlobalTag"