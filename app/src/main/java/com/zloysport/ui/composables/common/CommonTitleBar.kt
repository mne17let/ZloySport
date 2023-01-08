package com.zloysport.ui.composables.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zloysport.ui.theme.TitleSize

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