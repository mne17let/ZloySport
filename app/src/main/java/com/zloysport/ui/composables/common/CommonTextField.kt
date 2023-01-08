package com.zloysport.ui.composables.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zloysport.ui.theme.HintSize

@Composable
fun CommonTextField(
    @StringRes labelResource: Int,
    onValueChangedFromOutside: (newText: String) -> Unit = {}
) {
    var valueText by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = stringResource(id = labelResource),
                fontSize = HintSize
            )
        },
        value = valueText,
        onValueChange = { newText ->
            onValueChangedFromOutside(newText)
            valueText = newText
        }
    )
}