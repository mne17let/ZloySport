package com.zloysport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.R

@Composable
fun TrainingList() {
    Column {
        TitleBar(
            R.drawable.ic_back,
            R.string.trainings_title,
            R.drawable.ic_close,
        )
    }
}

@Composable
fun TitleBar(
    leftIcon: Int,
    title: Int,
    rightIcon: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.width(36.dp),
            painter = painterResource(id = leftIcon),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.background(Color.Green),
            text = stringResource(id = title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            modifier = Modifier.width(36.dp),
            painter = painterResource(id = rightIcon),
            contentDescription = ""
        )
    }
}