package com.zloysport.ui.setupreps.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zloysport.R
import com.zloysport.ui.CommonViewModel
import com.zloysport.ui.setupreps.composables.common.CommonActionButton
import com.zloysport.ui.setupreps.composables.common.CommonTitleBar

@Composable
fun ScreenAllDrills(
    viewModel: CommonViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CommonTitleBar(
            leftIconResId = R.drawable.ic_back,
            rightIconResId = R.drawable.ic_close,
            titleResId = R.string.all_drills_title
        )
        AllDrillsContent(
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
private fun AllDrillsContent(
    viewModel: CommonViewModel,
    navController: NavController
) {
    if (viewModel.haveDrills) {
        HaveDrillsState()
    } else {
        EmptyStateDrills(navController = navController)
    }
}

@Composable
private fun EmptyStateDrills(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(2.dp),
                text = "У вас нет тренировок",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            CommonActionButton(
                textResourceId = R.string.create_drill_action,
                onClick = { navController.navigate("set_drill_name")}
            )
        }
    }
}

@Composable
private fun HaveDrillsState() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(10000) {
            Text(text = "Элемент $it")
        }
    }
}