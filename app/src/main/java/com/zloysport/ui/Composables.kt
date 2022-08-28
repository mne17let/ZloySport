package com.zloysport.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.R

@Preview
@Composable
fun EnterTrainingNameScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        TitleBar(
            R.drawable.ic_back,
            R.string.trainings_title,
            R.drawable.ic_close,
        )

        EnterTrainingName()
        ConfirmButton(R.string.confirm_button_title)
    }
}

@Preview
@Composable
fun EnterTrainingName() {

    var valueText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.enter_training_name)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.enter_training_name_label))
            },
            value = valueText,
            onValueChange = { newText ->
                valueText = newText
            }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            painter = painterResource(id = leftIcon),
            contentDescription = ""
        )
        Text(
            text = stringResource(id = title),
            fontSize = 22.sp,
        )
        Icon(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            painter = painterResource(id = rightIcon),
            contentDescription = ""
        )
    }


}

@Composable
fun ConfirmButton(@StringRes stringResourceId: Int) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 106.dp)
            .height(60.dp),
        onClick = { }) {
        Text(text = stringResource(stringResourceId))
    }

}