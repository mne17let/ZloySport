package com.zloysport.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zloysport.ui.setupreps.composables.ScreenAllDrills
import com.zloysport.ui.setupreps.composables.ScreenDrill
import com.zloysport.ui.setupreps.composables.ScreenEnterTrainingName

class MainActivity : ComponentActivity() {

    val commonViewModel = CommonViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "all_drills") {
                composable("all_drills") {
                    ScreenAllDrills(
                        viewModel = commonViewModel,
                        navController = navController
                    )
                }
                composable("set_drill_name") { ScreenEnterTrainingName() }
                composable("drill") { ScreenDrill() }
            }
        }
    }
}

const val LogTag = "GlobalTag"