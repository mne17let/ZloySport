package com.zloysport.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zloysport.ui.setupreps.composables.ScreenAllDrills

class MainActivity : ComponentActivity() {
    
    val commonViewModel = CommonViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenAllDrills(viewModel = commonViewModel)
        }
    }
}