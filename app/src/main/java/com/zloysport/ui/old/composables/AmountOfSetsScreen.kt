package com.zloysport.ui.old.composables


import com.zloysport.ui.old.composables.common.ListItemPicker
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zloysport.R
import com.zloysport.ui.composables.common.CommonConfirmButton
import com.zloysport.ui.composables.common.CommonTitleBar
import com.zloysport.ui.theme.InfoSize

@Composable
fun AmountOfSetsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CommonTitleBar(
            leftIconResId = R.drawable.ic_back,
            title = stringResource(R.string.create_drill_title),
            rightIconResId = R.drawable.ic_close,
        )
        Text(
            modifier = Modifier.padding(horizontal = 80.dp),
            text = stringResource(R.string.choise_amount_of_sets),
            fontSize = InfoSize,
            textAlign = TextAlign.Center,
        )
        var selectedValue by remember { mutableStateOf(3) }

        Box {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(115.dp)
                    .border(width = 6.dp, color = Color(0xFFACDCFF), shape = CircleShape)
                    .align(Alignment.Center)
            )
            ListItemPicker(
                modifier = Modifier
                    .height(63.dp)
                    .align(Alignment.Center),
                value = selectedValue,
                list = (1..10).toList(),
                onValueChange = { selectedValue = it }
            )
        }

        CommonConfirmButton(textResourceId = R.string.next_button_title, onClick = {
        })
    }
}