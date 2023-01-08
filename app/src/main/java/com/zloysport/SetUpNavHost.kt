package com.zloysport

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zloysport.ui.ALL_DRILLS
import com.zloysport.ui.LOGIN
import com.zloysport.ui.SET_DRILL_NAME
import com.zloysport.ui.composables.ScreenAllDrills
import com.zloysport.ui.composables.ScreenLogin
import com.zloysport.ui.composables.ScreenSetDrillName
import com.zloysport.ui.states.AllDrillsStateHolder
import com.zloysport.ui.states.LoginStateHolder

@Composable
fun SetUpNavHost(
    navController: NavHostController,
    hasAccount: Boolean,
    loginStateHolder: LoginStateHolder,
    allDrillsStateHolder: AllDrillsStateHolder
) {
    NavHost(navController = navController, startDestination = if (hasAccount) ALL_DRILLS else LOGIN) {
        composable(LOGIN) {
            ScreenLogin(loginStateHolder)
        }
        composable(ALL_DRILLS) {
            ScreenAllDrills(
                allDrillsStateHolder = allDrillsStateHolder,
                navController = navController
            )
        }
        composable(SET_DRILL_NAME) {
            ScreenSetDrillName(navController = navController)
        }
    }
}