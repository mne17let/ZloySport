package com.zloysport.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zloysport.ui.composables.ScreenAllDrills
import com.zloysport.ui.composables.ScreenDrill
import com.zloysport.ui.composables.ScreenEnterTrainingName
import com.zloysport.ui.composables.ScreenTimer
import com.zloysport.ui.util.*

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
        NavHost(navController = navController, startDestination = DRILL) {
            composable(ALL_DRILLS) {
                ScreenAllDrills(
                    viewModel = commonViewModel,
                    navController = navController
                )
            }
            composable(SET_DRILL_NAME) {
                ScreenEnterTrainingName(
                    viewModel = commonViewModel,
                    navController = navController
                )
            }
            composable(DRILL) { ScreenDrill(viewModel = commonViewModel) }
            composable(TIMER) { ScreenTimer(20L, navController) }
        }
    }
}

const val LogTag = "GlobalTag"