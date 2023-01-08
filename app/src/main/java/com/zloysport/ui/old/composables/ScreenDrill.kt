package com.zloysport.ui.old.composables

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.R
import com.zloysport.data.Drill
import com.zloysport.data.Range
import com.zloysport.ui.states.AllDrillsStateHolder
import com.zloysport.ui.composables.CommonTitleBar
import com.zloysport.ui.composables.LineSlider
import com.zloysport.ui.old.ui.theme.DarkGreen
import com.zloysport.ui.old.ui.theme.DarkestBlue
import com.zloysport.ui.old.ui.theme.LightBlue
import com.zloysport.ui.old.ui.theme.LightGreen

@Composable
fun ScreenDrill(
    viewModel: AllDrillsStateHolder
) {
    Column {
        CommonTitleBar(
            R.drawable.ic_back,
            stringResource(id = R.string.drill_title),
            R.drawable.ic_close
        )
        DrillInfo(
            Drill(
                "Моя тренировка",
                viewModel.rangeList,
                relaxTime = 120,
                2
            )
        )
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
    ) {
        items(drillInfo.rangeList.size) {
            val range = drillInfo.rangeList[it]
            val backColor = if (range.alreadyDone) LightGreen else LightBlue
            val textColor = if (range.alreadyDone) DarkGreen else DarkestBlue
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(80.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .clip(shape = CircleShape)


                    .background(backColor)

                    .clickable(enabled = true,
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(),
                        onClick = {}), contentAlignment = Alignment.Center
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
    LineSlider(
        range.toLineSliderRange()
    )
}

@Preview
@Composable
fun ScreenDrillPreview() {
    ScreenDrill(
        viewModel = AllDrillsStateHolder()
    )
}