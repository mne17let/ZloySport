package com.zloysport.ui.setupreps.composables

import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.ui.LogTag
import com.zloysport.ui.MainActivity
import com.zloysport.ui.theme.*
import java.lang.Math.*
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.atan2

@Composable
fun ScreenDrill() {
    Column {
//        DrillInfo(
//            DrillInfo(
//                listOf(
//                    Range(1, true),
//                    Range(10, false),
//                    Range(200, false),
//                    Range(999, false),
//                    Range(5, false),
//                    Range(67, false),
//                    Range(67, false),
//                    Range(165, false),
//                    Range(28, true),
//                    Range(29, false),
//                    Range(162, true),
//                    Range(271, false),
//                    Range(63, true),
//                    Range(29, false)
//                )
//            )
//        )
        RangeInfo(
            Range(
                1,
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

    var isInitial by remember { mutableStateOf(true) }

    var handOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    var currentHandAngle by remember { mutableStateOf(0f) }

    val handAngle by animateFloatAsState(targetValue = currentHandAngle)

    var outSideOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    var currentAction by remember { mutableStateOf(0) }

    var startOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    var falseOffsets by remember { mutableStateOf(listOf<Offset>()) }

    var trueOffsets by remember { mutableStateOf(listOf<Offset>()) }

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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .padding(24.dp)
                .size(250.dp)
                .pointerInput(Unit) {
//                    detectTapGestures(onPress = {
//                        Log.d(LogTag, "RangeInfo: нажат $it || у точки координаты == $handOffset")
//                        if (isHitHand(handOffset, it)) {
//                            touchOffset = it
//                            isHit = true
//                            Toast
//                                .makeText(context, "Попал", Toast.LENGTH_SHORT)
//                                .show()
//
//                            val newList = mutableListOf<Offset>()
//
//                            newList.addAll(trueOffsets)
//                            newList.add(it)
//
//                            trueOffsets = newList
//                        } else {
//                            touchOffset = it
//                            isHit = false
//                            Toast
//                                .makeText(context, "Мимо", Toast.LENGTH_SHORT)
//                                .show()
//
//                            val newList = mutableListOf<Offset>()
//
//                            newList.addAll(falseOffsets)
//                            newList.add(it)
//
//                            falseOffsets = newList
//                        }
//                    })

                    detectDragGestures(
                        onDragStart = { touch ->
                            Log.d(LogTag, "Драг стартовал в точке $touch ||")

                            isHit = isHitHand(handOffset, touch)

                            if (!isHit) {
                                val list = mutableListOf<Offset>()
                                list.addAll(falseOffsets)
                                list.add(touch)

                                falseOffsets = list
                            }

                        },
                        onDrag = { change, offset ->
                            if (isHit) {
                                if (isOnTheLine(
                                        change.position,
                                        radius,
                                        center,
                                        misStep = 100f
                                    )
                                ) {
                                    val list = mutableListOf<Offset>()

                                    list.addAll(trueOffsets)
                                    list.add(change.position)

                                    isInitial = false

                                    val currentOffsets = getDrawOffset(
                                        change.position,
                                        radius,
                                        center,
                                        startOffset,
                                        oneAngle
                                    )

                                    val currentAngle = currentOffsets.angle

                                    handOffset = currentOffsets.handCenterOffset

//                                    val listOfKeys = LinkedList<Double>()
//                                    listOfKeys.addAll(anglesList.keys)
//
//                                    val iterator = listOfKeys.iterator()
//
//                                    var findAngle = 0.0
//
//                                    while (iterator.hasNext()) {
//                                        val current = iterator.next()
//                                        val diff = currentAngle - current
//
//                                        if (diff < oneAngle) {
//                                            if (diff <= oneAngle / 2.0) {
//                                                findAngle = current
//                                            } else {
//                                                findAngle = iterator.next()
//                                            }
//                                            break
//                                        }
//                                    }
//
//                                    anglesList[findAngle]?.let {
//                                        currentAction = it
//                                    }
//
//                                    val handY = center.y - Math.sin(findAngle) * radius
//                                    val handX = center.x - Math.cos(findAngle) * radius
//
//                                    handOffset = Offset(handX.toFloat(), handY.toFloat())

                                    outSideOffset = currentOffsets.handOutSideOffset

                                    trueOffsets = list
                                } else {
                                    val list = mutableListOf<Offset>()

//                                    list.addAll(falseOffsets)
//                                    list.add(change.position)

//                                    falseOffsets = list
                                }
                            }
                        },
                        onDragEnd = {
                            isHit = false
                        }
                    )


                }
//                .background(DarkestBlue)
        ) {

            android.util.Log.d(LogTag, "Рекомпозиция канаваса. Координаты ручки = $handOffset")
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

            center = Offset(size.width / 2, size.height / 2)

            radius = size.minDimension / 2 - strokeWidth / 2
            val longRadius = size.minDimension / 2 + 16 - strokeWidth / 2
            val shortRadius = size.minDimension / 2 - 16 - strokeWidth / 2
//            val oneAngle = (180 / range.count) * Math.PI / 180

            var angle = 0.0

            for (action in 0..range.count) {
                val nextX = size.width / 2 - radius * Math.cos(angle)
                val nextY = size.height / 2 - radius * Math.sin(angle)

                val nextXStart = size.width / 2 - longRadius * Math.cos(angle)
                val nextYStart = size.height / 2 - longRadius * Math.sin(angle)

                val nextXEnd = size.width / 2 - shortRadius * Math.cos(angle)
                val nextYEnd = size.height / 2 - shortRadius * Math.sin(angle)

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
            }

            drawCircle(
                color = if (isHit) Color.Red else Color.Cyan,
                radius = 60f,
                center = if (isHit) outSideOffset else handOffset
            )

            val text = currentAction

            val offsetDiff: Float
            if (text < 10) {
                offsetDiff = 8f
            } else if (text < 100) {
                offsetDiff = 16f
            } else {
                offsetDiff = 24f
            }

            if (isHit) {
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
//                    "${angle * 180 / Math.PI}",
//                    "${oneAngle * 180 / PI} || ${oneAngle * 180 / PI * 180}",
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
//                    "${angle * 180 / Math.PI}",
//                    "${oneAngle * 180 / PI} || ${oneAngle * 180 / PI * 180}",
                        text.toString(),
                        handOffset.x - offsetDiff,
                        handOffset.y + 12f,
                        Paint().apply {
                            textSize = 24f
                            color = Color.White.toArgb()
                        }
                    )
                }
            }


//            for (offset in trueOffsets) {
//                drawCircle(
//                    color = Color.Red,
//                    radius = 8f,
//                    center = offset
//                )
//            }
//
//            for (offset in falseOffsets) {
//                drawCircle(
//                    color = Color.Black,
//                    radius = 8f,
//                    center = offset
//                )
//            }

//            drawIntoCanvas {
//                it.nativeCanvas.drawText(
////                    "${angle * 180 / Math.PI}",
////                    "${oneAngle * 180 / PI} || ${oneAngle * 180 / PI * 180}",
//                    "|| ${oneAngle * 180 / PI * range.count}",
//                    size.width / 2,
//                    size.height / 2,
//                    Paint().apply {
//                        textSize = 24f
//                    }
//                )
//            }

            drawLine(
                color = Color.Black,
                start = center,
                end = handOffset
            )

//            if (touchOffset != Offset(0f, 0f)) {
//                drawCircle(
//                    color = if (isHit) Color.Black else Color.Red,
//                    radius = 5f,
//                    center = touchOffset
//                )
//            }
        }
    }
}

@Composable
private fun RangeInfoOld() {
    var radius by remember {
        mutableStateOf(0f)
    }

    var shapeCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var handleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var angle by remember {
        mutableStateOf(20.0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkestBlue),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .background(LightBlue)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        handleCenter += dragAmount

                        angle = getRotationAngle(handleCenter, shapeCenter)
                        change.consumeAllChanges()
                    }
                }
                .padding(16.dp)

        ) {
            shapeCenter = center

            radius = size.minDimension / 2

            val x = (shapeCenter.x + cos(Math.toRadians(angle)) * radius).toFloat()
            val y = (shapeCenter.y + sin(Math.toRadians(angle)) * radius).toFloat()

            handleCenter = Offset(x, y)

            drawCircle(
                color = Color.Black.copy(alpha = 0.10f),
                style = Stroke(20f),
                radius = radius
            )
            drawArc(
                size = Size(radius * 2, radius * 2),
                color = Color.Yellow,
                startAngle = 0f,
                sweepAngle = angle.toFloat(),
                useCenter = false,
                style = Stroke(20f),
            )

            drawCircle(color = Color.Cyan, center = handleCenter, radius = 60f)
        }
    }
}

private fun getRotationAngle(currentPosition: Offset, center: Offset): Double {
    val (dx, dy) = currentPosition - center
    val theta = atan2(dy, dx).toDouble()

    var angle = Math.toDegrees(theta)

    if (angle < 0) {
        angle += 360.0
    }
    return angle
}

private fun isHitHand(handCenterOffset: Offset, touchOffset: Offset): Boolean {
    val difVert = handCenterOffset.y - touchOffset.y
    val difHor = handCenterOffset.x - touchOffset.x
    val vert = -100 < difVert && difVert < 100
    val hor = -100 < difHor && difHor < 100

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

    val currentRadius = Math.sqrt((x * x + y * y).toDouble())

    val isAboveHorizon = touchOffset.y - center.y <= 0

    return currentRadius > shortRadius && currentRadius < longRadius && isAboveHorizon
}

private fun getDrawOffset(
    touchOffset: Offset,
    radius: Float,
    center: Offset,
    startOffset: Offset,
    oneAngle: Double
): CurrentOffsets {

    val isRight = touchOffset.x - center.x >= 0

    val nulVectorX = startOffset.x - center.x
    val nulVectorY = startOffset.y - center.y

    val nulLong = Math.sqrt((nulVectorX * nulVectorX + nulVectorY * nulVectorY).toDouble())

    val touchVectorX = touchOffset.x - center.x
    val touchVectorY = touchOffset.y - center.y

    val touchLong =
        Math.sqrt((touchVectorX * touchVectorX + touchVectorY * touchVectorY).toDouble())

    val scalar = nulVectorX * touchVectorX + nulVectorY * touchVectorY

    val prDlin = nulLong * touchLong

    val cos = scalar / prDlin


    android.util.Log.d(LogTag, "Косинус == $cos")

    val phi = Math.acos(cos)

    if (phi <= oneAngle / 2) {
        Log.d(LogTag, "getDrawOffset: ОКОЛО НУЛЯ")
    }

    val radX = center.x - radius * cos
    val outSideRadius = radius + 100
    val radXOutside = center.x - outSideRadius * cos
    val radXLong = radX - center.x
    val radXLongOutSide = radXOutside - center.x

    val radY = center.y - Math.sqrt(radius * radius - radXLong * radXLong)
    val radYOutSide =
        center.y - Math.sqrt(outSideRadius * outSideRadius - radXLongOutSide * radXLongOutSide)

    android.util.Log.d(
        LogTag, "Угол == ${phi * 57.3}. Радиус == $radius || касание = $touchOffset " +
                "|| start == $startOffset || XXX == $radX || COS == $cos"
    )

    android.util.Log.d(LogTag, "Угол == ${phi * 57.3}. XXX == $radX")


    return CurrentOffsets(
        Offset(radX.toFloat(), radY.toFloat()),
        Offset(radXOutside.toFloat(), radYOutSide.toFloat()),
        phi
    )
}

private fun getAnimatedOffset(
    anglesList: Map<Double, Int>,
    currentAngle: Double,
    oneAngle: Double,
    center: Offset,
    radius: Float,
): AnimatedData {
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

    var currentAction: Int = -1
    anglesList[findAngle]?.let {
        currentAction = it
    }

    val handY = center.y - Math.sin(findAngle) * radius
    val handX = center.x - Math.cos(findAngle) * radius

    val handOffset = Offset(handX.toFloat(), handY.toFloat())

    return AnimatedData(
        handOffset,
        currentAction
    )
}

data class DrillInfo(
    val rangeList: List<Range>
)

data class Range(
    val count: Int,
    val alreadyDone: Boolean
)

data class CurrentOffsets(
    val handCenterOffset: Offset,
    val handOutSideOffset: Offset,
    val angle: Double
)

data class AnimatedData(
    val handOffset: Offset,
    val action: Int
)

const val SMALL_SIZE = 20
const val MEDIUM_SIZE = 50
const val LARGE_SIZE = 100

const val SMALL_STEP = 2
const val MEDIUM_STEP = 5
const val LARGE_STEP = 10

@Preview
@Composable
fun ScreenDrillPreview() {
    ScreenDrill()
}