package com.zloysport.helpful

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.*
import kotlin.math.*

private data class SliderRange(val count: Int)

@Composable
fun CircleSlider() {
    RangeInfo(
        SliderRange(
            1
        )
    )
}

@Composable
private fun RangeInfo(
    range: SliderRange,
    canvasSize: Dp = 400.dp,
    handRadiusConst: Float = 60f,
    rippleRadiusConst: Float = 60f,
    shadowRadiusConst: Float = 75f,
    shadowRadiusNull: Float = 1f,
    rippleRadiusNull: Float = 0f,
    outSidePaddingConst: Float = 100f,
    outSideRadiusConst: Float = 60f
) {
    var isInitial by remember { mutableStateOf(true) }

    var handOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    var rippleRadius by remember { mutableStateOf(0f) }
    val animationRippleRadius = animateFloatAsState(
        targetValue = rippleRadius,
        animationSpec = tween(
            durationMillis = 100
        )
    )

    var shadowRadius by remember { mutableStateOf(75f) }
    val animationShadowRadius = animateFloatAsState(
        targetValue = shadowRadius
    )

    var outSideOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    var currentAction by remember { mutableStateOf(0) }

    var startOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    var isHit by remember { mutableStateOf(false) }

    var radius by remember { mutableStateOf(0f) }

    var center by remember { mutableStateOf(Offset(0f, 0f)) }

    val oneAngle = PI / range.count

    val anglesList = mutableMapOf<Double, Int>().apply {
        var startAngle = 0.0
        for (action in 0..range.count) {
            put(startAngle, action)
            startAngle += oneAngle
        }
    }

    Box(
        modifier = Modifier
            .size(canvasSize)
            .padding(Dp(60f)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { touch ->
                            isHit = isHitHand(handOffset, touch, misStep = handRadiusConst)
                        },
                        onDrag = { change, offset ->
                            if (isHit) {
                                if (isOnTheLine(
                                        change.position,
                                        radius,
                                        center,
                                        misStep = radius
                                    )
                                ) {
                                    isInitial = false

                                    val currentOffsets = getDrawOffset(
                                        change.position,
                                        radius,
                                        center,
                                        startOffset,
                                        outSidePaddingConst
                                    )

                                    val currentAngle = currentOffsets.angle

                                    handOffset = currentOffsets.handCenterOffset

                                    val listOfKeys = LinkedList<Double>()
                                    listOfKeys.addAll(anglesList.keys)

                                    val iterator = listOfKeys.iterator()

                                    var findAngle = 0.0

                                    while (iterator.hasNext()) {
                                        val current = iterator.next()
                                        val diff = currentAngle - current

                                        if (diff < oneAngle) {
                                            findAngle = if (diff <= oneAngle / 2.0) {
                                                current
                                            } else {
                                                iterator.next()
                                            }
                                            break
                                        }
                                    }

                                    anglesList[findAngle]?.let {
                                        currentAction = it
                                    }

                                    val handY = center.y - sin(findAngle) * radius
                                    val handX = center.x - cos(findAngle) * radius

                                    handOffset = Offset(handX.toFloat(), handY.toFloat())

                                    outSideOffset = currentOffsets.handOutSideOffset
                                } else {
                                    val currentOffsets = getDrawOffset(
                                        handOffset,
                                        radius,
                                        center,
                                        startOffset,
                                        outSidePaddingConst
                                    )

                                    outSideOffset = currentOffsets.handOutSideOffset
                                }
                            }
                        },
                        onDragEnd = {
                            rippleRadius = rippleRadiusNull

                            isHit = false

                            shadowRadius = shadowRadiusConst
                        }
                    )


                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            if (isHitHand(handOffset, it, misStep = handRadiusConst)
                            ) {
                                rippleRadius = rippleRadiusConst
                                shadowRadius = shadowRadiusNull
                            }

                            awaitRelease()
                            isHit = false

                            rippleRadius = rippleRadiusNull
                            shadowRadius = shadowRadiusConst
                        },
                        onLongPress = { touchOffset ->
                            if (isHitHand(handOffset, touchOffset, misStep = handRadiusConst)
                            ) {
                                isHit = true
                                outSideOffset = getDrawOffset(
                                    handOffset,
                                    radius,
                                    center,
                                    startOffset,
                                    outSidePaddingConst
                                ).handOutSideOffset
                            }

                            rippleRadius = rippleRadiusNull
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { touch ->
                            isHit = isHitHand(handOffset, touch, misStep = handRadiusConst)
                        },
                        onDrag = { change, offset ->
                            if (isHit) {
                                if (isOnTheLine(
                                        change.position,
                                        radius,
                                        center,
                                        misStep = radius
                                    )
                                ) {
                                    isInitial = false

                                    val currentOffsets = getDrawOffset(
                                        change.position,
                                        radius,
                                        center,
                                        startOffset,
                                        outSidePaddingConst
                                    )

                                    val currentAngle = currentOffsets.angle

                                    val listOfKeys = LinkedList<Double>()
                                    listOfKeys.addAll(anglesList.keys)

                                    val iterator = listOfKeys.iterator()

                                    var findAngle = 0.0

                                    while (iterator.hasNext()) {
                                        val current = iterator.next()
                                        val diff = currentAngle - current

                                        if (diff < oneAngle) {
                                            if (diff <= oneAngle / 2.0) {
                                                findAngle = current
                                            } else {
                                                findAngle = iterator.next()
                                            }
                                            break
                                        }
                                    }

                                    anglesList[findAngle]?.let {
                                        currentAction = it
                                    }

                                    val handY = center.y - sin(findAngle) * radius
                                    val handX = center.x - cos(findAngle) * radius

                                    handOffset = Offset(handX.toFloat(), handY.toFloat())

                                    outSideOffset = currentOffsets.handOutSideOffset
                                } else {
                                    val currentOffsets = getDrawOffset(
                                        handOffset,
                                        radius,
                                        center,
                                        startOffset,
                                        outSidePaddingConst
                                    )

                                    outSideOffset = currentOffsets.handOutSideOffset
                                }
                            }
                        },
                        onDragEnd = {
                            shadowRadius = shadowRadiusConst
                            rippleRadius = rippleRadiusNull
                            isHit = false
                        }
                    )
                }
        ) {

            val strokeWidth = 20f
            val topLeftX = (size.width / 2f) - (size.minDimension / 2f) + handRadiusConst
            val topLeftY = (size.height / 2f) - (size.minDimension / 2f) + handRadiusConst
            val topLeft = Offset(topLeftX, topLeftY)
            drawArc(
                color = DarkGray,
                startAngle = 0f,
                sweepAngle = -180f,
                useCenter = false,
                size = Size(
                    size.minDimension - handRadiusConst * 2,
                    size.minDimension - handRadiusConst * 2
                ),
                alpha = 1f,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = topLeft
            )

            center = Offset(size.width / 2, size.height / 2)

            radius = size.minDimension / 2 - handRadiusConst
            val longRadius = radius + 16
            val shortRadius = radius - 16

            var angle = 0.0

            for (action in 0..range.count) {
                val nextX = size.width / 2 - radius * cos(angle)
                val nextY = size.height / 2 - radius * sin(angle)

                val nextXStart = size.width / 2 - longRadius * cos(angle)
                val nextYStart = size.height / 2 - longRadius * sin(angle)

                val nextXEnd = size.width / 2 - shortRadius * cos(angle)
                val nextYEnd = size.height / 2 - shortRadius * sin(angle)

                if (range.count < SMALL_SIZE) {
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

                } else if (range.count < MEDIUM_SIZE) {
                    if (range.count == action || (action % MEDIUM_STEP == 0 && (range.count - action) >= MEDIUM_STEP / 2.0)) {
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
                    if (range.count == action || (action % LARGE_STEP == 0 && (range.count - action) >= LARGE_STEP / 2.0)) {
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

            if (isInitial) {
                handOffset = Offset(center.x - radius, center.y)
                startOffset = Offset(center.x - radius, center.y)
                outSideOffset = Offset(center.x - radius, center.y) - Offset(100f, 0f)
            }

            if (!isHit) {

                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(
                            ShadowGrayAlpha50,
                            Transparent
                        ),
                        center = handOffset,
                        radius = animationShadowRadius.value
                    ),
                    radius = 75f,
                    center = handOffset
                )

                drawCircle(
                    color = LightBlue,
                    radius = handRadiusConst,
                    center = handOffset
                )

                drawCircle(
                    color = RippleGrayAlpha20,
                    radius = if (rippleRadius == 0f) 0f else animationRippleRadius.value,
                    center = handOffset
                )
            } else {
                drawCircle(
                    color = Color.Red,
                    radius = outSideRadiusConst,
                    center = outSideOffset
                )
            }

            val text = currentAction

            val offsetDiff = if (text < 10) {
                8f
            } else if (text < 100) {
                16f
            } else {
                24f
            }

            if (isHit) {
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        text.toString(),
                        outSideOffset.x - offsetDiff,
                        outSideOffset.y + 12f,
                        Paint().apply {
                            textSize = 24f
                            color = Color.White.toArgb()
                        }
                    )
                }
            } else {
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        text.toString(),
                        handOffset.x - offsetDiff,
                        handOffset.y + 12f,
                        Paint().apply {
                            textSize = 24f
                            color = DarkestBlue.toArgb()
                        }
                    )
                }
            }

            drawLine(
                color = Color.Black,
                start = center,
                end = handOffset
            )
        }
    }
}

private fun isHitHand(handCenterOffset: Offset, touchOffset: Offset, misStep: Float): Boolean {
    val difVert = handCenterOffset.y - touchOffset.y
    val difHor = handCenterOffset.x - touchOffset.x
    val vert = -misStep < difVert && difVert < misStep
    val hor = -misStep < difHor && difHor < misStep

    return vert && hor
}

private fun isOnTheLine(
    touchOffset: Offset,
    radius: Float,
    center: Offset,
    misStep: Float
): Boolean {
    val longRadius = radius + misStep
    val shortRadius = radius - misStep

    val x = (touchOffset.x - center.x).absoluteValue
    val y = (touchOffset.y - center.y).absoluteValue

    val currentRadius = sqrt((x * x + y * y).toDouble())

    val isAboveHorizon = touchOffset.y - center.y <= 0

    return currentRadius > shortRadius && currentRadius < longRadius && isAboveHorizon
}

private fun getDrawOffset(
    touchOffset: Offset,
    radius: Float,
    center: Offset,
    startOffset: Offset,
    outSidePadding: Float
): CurrentOffsets {
    val nulVectorX = startOffset.x - center.x
    val nulVectorY = startOffset.y - center.y

    val nulLong = sqrt((nulVectorX * nulVectorX + nulVectorY * nulVectorY).toDouble())

    val touchVectorX = touchOffset.x - center.x
    val touchVectorY = touchOffset.y - center.y

    val touchLong =
        sqrt((touchVectorX * touchVectorX + touchVectorY * touchVectorY).toDouble())

    val scalar = nulVectorX * touchVectorX + nulVectorY * touchVectorY

    val prDlin = nulLong * touchLong

    val cos = scalar / prDlin

    val phi = acos(cos)

    val radX = center.x - radius * cos
    val outSideRadius = radius + outSidePadding
    val radXOutside = center.x - outSideRadius * cos
    val radXLong = radX - center.x
    val radXLongOutSide = radXOutside - center.x

    val radY = center.y - sqrt(radius * radius - radXLong * radXLong)
    val radYOutSide =
        center.y - sqrt(outSideRadius * outSideRadius - radXLongOutSide * radXLongOutSide)

    return CurrentOffsets(
        handCenterOffset = Offset(radX.toFloat(), radY.toFloat()),
        handOutSideOffset = Offset(radXOutside.toFloat(), radYOutSide.toFloat()),
        angle = phi
    )
}

data class CurrentOffsets(
    val handCenterOffset: Offset,
    val handOutSideOffset: Offset,
    val angle: Double
)

private const val SMALL_SIZE = 20
private const val MEDIUM_SIZE = 50
private const val LARGE_SIZE = 100

private const val SMALL_STEP = 2
private const val MEDIUM_STEP = 5
private const val LARGE_STEP = 10

private val LightBlue = Color(0xFFE8F4FB)
private val DarkestBlue = Color(0xFF004973)

private val DarkGray = Color(0xFF8C8F8B)

private val RippleGrayAlpha20 = Color(0x33434642)

val ShadowGrayAlpha50 = Color(0x80434642)
val Transparent = Color(0x00000000)