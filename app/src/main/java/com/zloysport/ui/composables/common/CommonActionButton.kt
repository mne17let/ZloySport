package com.zloysport.ui.composables.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zloysport.ui.theme.Blue
import com.zloysport.ui.theme.ButtonSize

@Composable
fun CommonActionButton(
    @StringRes textResourceId: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(corner = CornerSize(8.dp)))
            .padding(8.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Blue),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 0.dp),

            text = stringResource(textResourceId),
            color = Color.White,
            fontSize = ButtonSize,
            fontWeight = FontWeight.Normal
        )
    }
}