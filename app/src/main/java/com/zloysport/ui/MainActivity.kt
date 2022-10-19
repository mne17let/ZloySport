package com.zloysport.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zloysport.ui.setupreps.composables.ScreenAllDrills
import com.zloysport.ui.setupreps.composables.ScreenDrill
import com.zloysport.ui.setupreps.composables.ScreenEnterTrainingName
import com.zloysport.ui.setupreps.composables.ScreenTimer

class MainActivity : ComponentActivity() {

    val commonViewModel = CommonViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SetUpNavHost(navController = navController)
        }
    }

    @Composable
    private fun SetUpNavHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "timer") {
            composable("all_drills") {
                ScreenAllDrills(
                    viewModel = commonViewModel,
                    navController = navController
                )
            }
            composable("set_drill_name") {
                ScreenEnterTrainingName(
                    viewModel = commonViewModel,
                    navController = navController
                )
            }
            composable("drill") { ScreenDrill() }
            composable("timer") { ScreenTimer(20L, this@MainActivity) }
        }
    }
}

const val LogTag = "GlobalTag"