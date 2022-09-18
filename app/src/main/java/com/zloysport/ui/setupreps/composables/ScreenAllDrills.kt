package com.zloysport.ui.setupreps.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.R
import com.zloysport.ui.CommonViewModel
import com.zloysport.ui.setupreps.composables.common.CommonConfirmButton
import com.zloysport.ui.setupreps.composables.common.CommonTitleBar
import com.zloysport.ui.theme.MainLightBlue

@Composable
fun ScreenAllDrills(viewModel: CommonViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CommonTitleBar(
            titleResId = R.string.all_drills_title,
        )
        AllDrillsContent(viewModel = viewModel)
        if (!viewModel.haveDrills) {
            CommonConfirmButton(textResourceId = R.string.create_drill_action)
        }
    }
}

@Composable
private fun AllDrillsContent(viewModel: CommonViewModel) {
    if (viewModel.haveDrills) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(10000) {
                Text(text = "Элемент $it")
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(CornerSize(16.dp)))
                    .background(MainLightBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(30.dp),
                    text = "У вас нет тренировок",
                    fontSize = 22.sp,
                )
            }

        }
    }
}