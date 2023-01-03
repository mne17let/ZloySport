package com.zloysport.old.composables.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zloysport.old.ui.theme.ButtonSize
import com.zloysport.old.ui.theme.HintSize
import com.zloysport.old.ui.theme.TitleSize

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

@Composable
fun CommonConfirmButton(
    @StringRes textResourceId: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(corner = CornerSize(16.dp)))
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 32.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = com.zloysport.old.ui.theme.Blue),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp),
            text = stringResource(textResourceId),
            color = Color.White,
            fontSize = ButtonSize,
            fontWeight = FontWeight.Normal
        )
    }
}

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
        colors = ButtonDefaults.buttonColors(backgroundColor = com.zloysport.old.ui.theme.Blue),
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

@Composable
fun CommonTitleBar(
    @DrawableRes leftIconResId: Int? = null,
    title: String,
    @DrawableRes rightIconResId: Int? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconAreaSize = 60.dp

        val textStartPadding = if (leftIconResId == null) iconAreaSize else 0.dp
        val textEndPadding = if (rightIconResId == null) iconAreaSize else 0.dp

        val textVerticalPadding =
            if (leftIconResId == null && rightIconResId == null) iconAreaSize / 3 else 0.dp

        leftIconResId?.let {
            CommonRoundIconButton(
                modifier = Modifier,
                areaSize = iconAreaSize,
                iconRes = it
            )
        }

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = textStartPadding, end = textEndPadding)
                .padding(vertical = textVerticalPadding),
            text = title,
            fontSize = TitleSize,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        rightIconResId?.let {
            CommonRoundIconButton(
                modifier = Modifier,
                areaSize = iconAreaSize,
                iconRes = it
            )
        }
    }
}

@Composable
private fun CommonRoundIconButton(
    modifier: Modifier,
    areaSize: Dp,
    @DrawableRes iconRes: Int
) {
    Box(
        modifier = modifier
            .size(areaSize)
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = {},
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = modifier
                .size(areaSize / 2f),
            painter = painterResource(id = iconRes),
            contentDescription = "",
        )
    }
}