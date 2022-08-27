package com.zloysport.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(painter = painterResource(id = leftIcon), contentDescription = "")
        Text(text = stringResource(id = title))
        Icon(painter = painterResource(id = rightIcon), contentDescription = "")
    }
}