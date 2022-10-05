package com.zloysport.ui.setupreps.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.ui.theme.DarkGreen
import com.zloysport.ui.theme.DarkestBlue
import com.zloysport.ui.theme.LightBlue
import com.zloysport.ui.theme.LightGreen

@Composable
fun ScreenDrill() {
    Column {
        DrillInfo(
            DrillInfo(
                listOf(
                    Range(1, true),
                    Range(10, false),
                    Range(200, false),
                    Range(999, false),
                    Range(5, false),
                    Range(67, false),
                    Range(67, false),
                    Range(165, false),
                    Range(28, true),
                    Range(29, false),
                    Range(162, true),
                    Range(271, false),
                    Range(63, true),
                    Range(29, false)
                )
            )
        )
        RangeInfo()
    }
}

@Composable
private fun DrillInfo(drillInfo: DrillInfo) {
    LazyRow(modifier = Modifier
        .padding(vertical = 20.dp)
        ) {
        items(drillInfo.rangeList.size) {
            val range = drillInfo.rangeList[it]
            val modifier = Modifier
            val backColor = if (range.alreadyDone) LightGreen else LightBlue
            val textColor = if (range.alreadyDone) DarkGreen else DarkestBlue
            Box(
                modifier = modifier
                    .padding(8.dp)
                    .size(80.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .clip(shape = CircleShape)
                    .background(backColor)
                    .clickable(enabled = true,
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(),
                    onClick = {}),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = range.count.toString(),
                    color = textColor,
                    fontSize = 32.sp
                )
            }
        }
    }
}

@Composable
private fun RangeInfo() {

}

data class DrillInfo(
    val rangeList: List<Range>
)

data class Range(
    val count: Int,
    val alreadyDone: Boolean
)

@Preview
@Composable
fun ScreenDrillPreview() {
    ScreenDrill()
}