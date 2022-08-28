package com.zloysport.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.R

@Composable
fun EnterTrainingNameScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        TitleBar(
            R.drawable.ic_back,
            R.string.create_training_title,
            R.drawable.ic_close,
        )

        EnterTrainingNameTextField()
        ConfirmButton(R.string.confirm_button_title)
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
                .size(24.dp),
            painter = painterResource(id = leftIcon),
            contentDescription = ""
        )
        Text(
            text = stringResource(id = title),
            fontSize = 22.sp,
        )
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = rightIcon),
            contentDescription = ""
        )
    }
}

@Composable
fun EnterTrainingNameTextField() {

    var valueText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
fun ConfirmButton(@StringRes textResourceId: Int) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 32.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(16.dp))),
        onClick = { }) {
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp),
            text = stringResource(textResourceId)
        )
    }

}