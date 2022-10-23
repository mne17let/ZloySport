package com.zloysport.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.data.Drill
import com.zloysport.data.Range
import com.zloysport.ui.composables.common.CircleSlider
import com.zloysport.ui.theme.*

@Composable
fun ScreenDrill() {
    Column {
//        DrillInfo(
//            DrillInfo(
//                ranges = listOf(
//                    CircleSliderRange(1, true),
//                    CircleSliderRange(10, false),
//                    CircleSliderRange(200, false),
//                    CircleSliderRange(999, false),
//                    CircleSliderRange(5, false),
//                    CircleSliderRange(67, false),
//                    CircleSliderRange(67, false),
//                    CircleSliderRange(165, false),
//                    CircleSliderRange(28, true),
//                    CircleSliderRange(29, false),
//                    CircleSliderRange(162, true),
//                    CircleSliderRange(271, false),
//                    CircleSliderRange(63, true),
//                    CircleSliderRange(29, false)
//                )
//            )
//        )
        RangeInfo(
            Range(
                100,
                false
            )
        )
    }
}

@Composable
private fun DrillInfo(drillInfo: Drill) {
    LazyRow(
        modifier = Modifier
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
fun RangeInfo(range: Range) {
    CircleSlider(
        range.toCircleSliderRange()
    )
}

@Preview
@Composable
fun ScreenDrillPreview() {
    ScreenDrill()
}