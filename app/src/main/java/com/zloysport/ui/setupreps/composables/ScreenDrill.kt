package com.zloysport.ui.setupreps.composables

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.ui.theme.*
import java.lang.Math.*
import kotlin.math.atan2

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
        RangeInfo(
            Range(
                49,
                false
            )
        )
    }
}

@Composable
private fun DrillInfo(drillInfo: DrillInfo) {
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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
//                .background(DarkestBlue)
        ) {
            val strokeWidth = 20f
            val topLeftX = (size.width / 2f) - (size.minDimension / 2f) + (strokeWidth / 2)
            val topLeftY = (size.height / 2f) - (size.minDimension / 2f) + (strokeWidth / 2)
            val topLeft = Offset(topLeftX, topLeftY)
            drawArc(
                color = DarkGray,
                startAngle = 0f,
                sweepAngle = -180f,
                useCenter = false,
                size = Size(size.minDimension - strokeWidth, size.minDimension - strokeWidth),
                alpha = 1f,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = topLeft
            )

            val center = Offset(size.width / 2, size.height / 2)

            val radius = size.minDimension / 2 - strokeWidth / 2
            val longRadius = size.minDimension / 2 + 16 - strokeWidth / 2
            val shortRadius = size.minDimension / 2 - 16 - strokeWidth / 2
//            val oneAngle = (180 / range.count) * Math.PI / 180
            val oneAngle = PI / range.count
            val startX = 0f
            val startY = size.height / 2

            val startStart = Offset(startX + 100, startY + 0)
            val startEnd = Offset(startX - 100, startY + 0)

            var angle = 0.0

            for (action in 0..range.count) {
                val nextX = size.width / 2 - radius * Math.cos(angle)
                val nextY = size.height / 2 - radius * Math.sin(angle)

                val nextXStart = size.width / 2 - longRadius * Math.cos(angle)
                val nextYStart = size.height / 2 - longRadius * Math.sin(angle)

                val nextXEnd = size.width / 2 - shortRadius * Math.cos(angle)
                val nextYEnd = size.height / 2 - shortRadius * Math.sin(angle)

                if (range.count < 20) {
                    drawLine(
                        Color.Black,
                        start = Offset(nextXStart.toFloat(), nextYStart.toFloat()),
                        end = Offset(nextXEnd.toFloat(), nextYEnd.toFloat()),
                        strokeWidth = 8f
                    )

                    drawCircle(
                        color = Color.Cyan,
                        center = Offset(nextX.toFloat(), nextY.toFloat()),
                        radius = 4f
                    )

                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            "$action",
                            nextX.toFloat(),
                            nextY.toFloat(),
                            Paint().apply {
                                textSize = 24f
                            }
                        )
                    }

                } else if (range.count < 50) {
                    if (range.count == action || action % 5 == 0) {
                        drawLine(
                            Color.Black,
                            start = Offset(nextXStart.toFloat(), nextYStart.toFloat()),
                            end = Offset(nextXEnd.toFloat(), nextYEnd.toFloat()),
                            strokeWidth = 8f
                        )

                        drawCircle(
                            color = Color.Cyan,
                            center = Offset(nextX.toFloat(), nextY.toFloat()),
                            radius = 4f
                        )

                        drawIntoCanvas {
                            it.nativeCanvas.drawText(
                                "$action",
                                nextX.toFloat(),
                                nextY.toFloat(),
                                Paint().apply {
                                    textSize = 24f
                                }
                            )
                        }
                    }
                } else {
                    if (range.count == action || action % 10 == 0) {
                        drawLine(
                            Color.Black,
                            start = Offset(nextXStart.toFloat(), nextYStart.toFloat()),
                            end = Offset(nextXEnd.toFloat(), nextYEnd.toFloat()),
                            strokeWidth = 8f
                        )

                        drawCircle(
                            color = Color.Cyan,
                            center = Offset(nextX.toFloat(), nextY.toFloat()),
                            radius = 4f
                        )

                        drawIntoCanvas {
                            it.nativeCanvas.drawText(
                                "$action",
                                nextX.toFloat(),
                                nextY.toFloat(),
                                Paint().apply {
                                    textSize = 24f
                                }
                            )
                        }
                    }
                }

                angle += oneAngle
            }

            drawIntoCanvas {
                it.nativeCanvas.drawText(
//                    "${angle * 180 / Math.PI}",
//                    "${oneAngle * 180 / PI} || ${oneAngle * 180 / PI * 180}",
                    "|| ${oneAngle * 180 / PI * range.count}",
                    size.width / 2,
                    size.height / 2,
                    Paint().apply {
                        textSize = 24f
                    }
                )
            }
        }
    }
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