package com.zloysport.ui

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun TrainingList(context: Context) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        TitleBar(
            R.drawable.ic_back,
            R.string.trainings_title,
            R.drawable.ic_close,
        )

        EnterTrainingName(context)
        ConfirmButton(R.string.confirm_button_title, context)
    }
}

@Composable
fun EnterTrainingName(context: Context) {
    val valueState: MutableState<String> = remember { mutableStateOf("") }
    val placeholderState: MutableState<String> = remember { mutableStateOf("введите название") }
    val labelState: MutableState<String> = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Название"
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text(text = labelState.value) },
            placeholder = { Text(text = placeholderState.value) },
            value = valueState.value,
            onValueChange = { newText ->
                onValueChanged(newText, context, valueState, placeholderState, labelState)
            }
        )
    }
}

fun onValueChanged(
    newText: String,
    context: Context,
    valueState: MutableState<String>,
    placeholderState: MutableState<String>,
    labelState: MutableState<String>
) {
    if (newText == "") {
        placeholderState.value = "введите название"
        labelState.value = ""
    } else {
        placeholderState.value = ""
        labelState.value = "введите название"
    }
    valueState.value = newText
    Toast.makeText(context, newText, Toast.LENGTH_SHORT).show()

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
            modifier = Modifier.width(24.dp),
            painter = painterResource(id = leftIcon),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.background(Color.Green),
            text = stringResource(id = title),
            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold
        )
        Icon(
            modifier = Modifier.width(24.dp),
            painter = painterResource(id = rightIcon),
            contentDescription = ""
        )
    }


}

@Composable
fun ConfirmButton(@StringRes stringResourceId: Int, context: Context) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = { Toast.makeText(context, "КНООООПКА", Toast.LENGTH_SHORT).show() }) {
        Text(text = stringResource(stringResourceId))
    }
}