package com.zloysport.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zloysport.ui.theme.Blue
import kotlin.math.*

data class LineSliderRange(val count: Int)

const val LineSliderTag = "LineSliderTag"

@Composable
fun LineSlider(
    range: LineSliderRange = LineSliderRange(3)
) {
    var startMainLinePoint by remember { mutableStateOf(Offset(0f, 0f)) }
    var endMainLinePoint by remember { mutableStateOf(Offset(0f, 0f)) }
    var thirdMainLinePoint by remember { mutableStateOf(Offset(0f, 0f)) }

    var currentPointOnLine by remember { mutableStateOf(Offset(0f, 0f)) }
    var outSidePointOnTheLine by remember { mutableStateOf(Offset(0f, 0f)) }
    var currentChosenPoint by remember { mutableStateOf(Offset(0f, 0f)) }
    var currentChosenStep by remember { mutableStateOf(0) }

    var mainLineVector by remember { mutableStateOf(Offset(0f, 0f)) }
    var mainLineLong by remember { mutableStateOf(0f) }
    var oppositeCathetLong by remember { mutableStateOf(0f) }
    var sinMainAngle by remember { mutableStateOf(0f) }
    var mainAngle by remember { mutableStateOf(0f) }
    var betaAngle by remember { mutableStateOf(0f) }

    var misStepStartUpPoint by remember { mutableStateOf(Offset(0f, 0f)) }
    var misStepStartDownPoint by remember { mutableStateOf(Offset(0f, 0f)) }
    var misStepEndUpPoint by remember { mutableStateOf(Offset(0f, 0f)) }
    var misStepEndDownPoint by remember { mutableStateOf(Offset(0f, 0f)) }

    var listOfStrikes by remember { mutableStateOf(listOf<Offset>()) }
    var listOfMisses by remember { mutableStateOf(listOf<Offset>()) }

    var listOfAroundStart by remember { mutableStateOf(listOf<Offset>()) }
    var listOfAroundEnd by remember { mutableStateOf(listOf<Offset>()) }

    var isInitial by remember { mutableStateOf(true) }
    var isCurrentlyStrike by remember { mutableStateOf(false) }

    var oneStep by remember { mutableStateOf(0f) }
    var mapOfSteps by remember { mutableStateOf(mutableMapOf<Int, Float>()) }

    var radiusConst = 60f
    val misStep = 150f
    val misStepAlongLine = 150f
    val mainLineWidth = 10f
    val straightLongFromLine = 100f
    val straightLongFromLineForSteps = 100f

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    val isOnTheLineInfo = isOnTheLine(
                        it,
                        mainLineVector,
                        startMainLinePoint,
                        endMainLinePoint,
                        mainAngle,
                        misStep,
                        misStepAlongLine
                    )

                    val isOnTheLine = isOnTheLineInfo.isOnTheLine

                    if (isOnTheLine) {
/*                    val oldStrikes = listOfStrikes
                    val newStrikes = mutableListOf<Offset>()
                    newStrikes.addAll(oldStrikes)
                    newStrikes.add(it)
                    listOfStrikes = newStrikes\*/

                        val pointsInfo = getPointOnLineProectionInfo(
                            mainAngle,
                            isOnTheLineInfo.longOfTheTouchOnMainLine,
                            straightLongFromLine
                        )

                        currentPointOnLine = startMainLinePoint + pointsInfo.pointOnLineOffset
                        outSidePointOnTheLine =
                            currentPointOnLine + pointsInfo.outSideBottomRightOffset

                        currentChosenStep = getNearestStep(
                            mapOfSteps,
                            isOnTheLineInfo.longOfTheTouchOnMainLine,
                            oneStep
                        )

                        val stepLong = mapOfSteps[currentChosenStep]
                        if (stepLong != null) {
                            val currentChosenPointInfo = getPointOnLineProectionInfo(mainAngle, stepLong, 0f)

                            currentChosenPoint = startMainLinePoint + currentChosenPointInfo.pointOnLineOffset
                        }
                    } else {
/*                        val oldMisses = listOfMisses
                        val newMisses = mutableListOf<Offset>()
                        newMisses.addAll(oldMisses)
                        newMisses.add(it)
                        listOfMisses = newMisses*/
                    }
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
/*                    val dragStartPointInfo = isOnTheLine(
                        it,
                        mainLineVector,
                        startMainLinePoint,
                        endMainLinePoint,
                        mainAngle,
                        misStep
                    )

                    isCurrentlyStrike = dragStartPointInfo.isOnTheLine*/
                    },
                    onDrag = { change, currentDragOffset ->
                        val dragPointInfo = isOnTheLine(
                            change.position,
                            mainLineVector,
                            startMainLinePoint,
                            endMainLinePoint,
                            mainAngle,
                            misStep,
                            misStepAlongLine
                        )

                        isCurrentlyStrike =
                            dragPointInfo.isOnTheLine

                        if (isCurrentlyStrike) {

/*                            val oldStrikes = listOfStrikes
                            val newStrikes = mutableListOf<Offset>()
                            newStrikes.addAll(oldStrikes)
                            newStrikes.add(change.position)
                            listOfStrikes = newStrikes*/

                            val onLineInfo = getPointOnLineProectionInfo(
                                mainAngle,
                                dragPointInfo.longOfTheTouchOnMainLine,
                                straightLongFromLine
                            )

                            currentPointOnLine = startMainLinePoint + onLineInfo.pointOnLineOffset
                            outSidePointOnTheLine =
                                currentPointOnLine + onLineInfo.outSideBottomRightOffset

                            currentChosenStep = getNearestStep(
                                mapOfSteps,
                                dragPointInfo.longOfTheTouchOnMainLine,
                                oneStep
                            )

                            val stepLong = mapOfSteps[currentChosenStep]
                            if (stepLong != null) {
                                val currentChosenPointInfo = getPointOnLineProectionInfo(mainAngle, stepLong, 0f)

                                currentChosenPoint = startMainLinePoint + currentChosenPointInfo.pointOnLineOffset
                            }

                        } else {
/*                            val oldMisses = listOfMisses
                            val newMisses = mutableListOf<Offset>()
                            newMisses.addAll(oldMisses)
                            newMisses.add(change.position)
                            listOfMisses = newMisses*/
                        }

                    },

                    onDragEnd = {
                        isCurrentlyStrike = false
                    }
                )
            }
        ) {
            if (isInitial) {
                isInitial = false
            }

            startMainLinePoint = Offset(0f + 400f, size.height - 400f)
            endMainLinePoint = Offset(size.width - 400f, 0f + 400f)
            thirdMainLinePoint = Offset(size.width - 400f, size.height - 400f)

//            startMainLinePoint = Offset(0f, size.height)
//            endMainLinePoint = Offset(size.width, 0f)
//            thirdMainLinePoint = Offset(size.width, size.height)

            val oppositeCathetVector = thirdMainLinePoint - endMainLinePoint
            mainLineVector = endMainLinePoint - startMainLinePoint

            mainLineLong =
                sqrt(mainLineVector.x * mainLineVector.x + mainLineVector.y * mainLineVector.y)
            oppositeCathetLong =
                sqrt(oppositeCathetVector.x * oppositeCathetVector.x + oppositeCathetVector.y * oppositeCathetVector.y)

            oneStep = mainLineLong / range.count

            var currentStep = 0f
            for (i in 0..range.count) {
                mapOfSteps[i] = currentStep
                currentStep += oneStep
            }

            for (i in 0..range.count) {
                val current = mapOfSteps[i]
                if (current != null) {
                    val pointInfo = getPointOnLineProectionInfo(
                        mainAngle,
                        current,
                        straightLongFromLineForSteps
                    )

                    val point = startMainLinePoint + pointInfo.pointOnLineOffset
                    val startPoint = point + pointInfo.outSideBottomRightOffset
                    val endPoint = point - pointInfo.outSideBottomRightOffset

                    drawLine(
                        color = Color.Green,
                        start = startPoint,
                        end = endPoint,
                        strokeWidth = 20f
                    )

                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            "$i",
                            point.x - 50,
                            point.y + 50,
                            android.graphics.Paint().apply {
                                textSize = 40f
                            }
                        )
                    }
                }
            }

            sinMainAngle = oppositeCathetLong / mainLineLong
            mainAngle = asin(sinMainAngle)
            betaAngle = (PI / 2 - mainAngle).toFloat()

            val misStepAlongLineOffset =
                getMisStepAlongLineProectionOffset(misStepAlongLine, mainAngle)
            val startMinusMisStepAlongLine = startMainLinePoint - misStepAlongLineOffset
            val endPlusMisStepAlongLine = endMainLinePoint + misStepAlongLineOffset

            misStepStartUpPoint =
                getMisStepPoint(true, misStep, startMinusMisStepAlongLine, mainAngle)
            misStepStartDownPoint =
                getMisStepPoint(false, misStep, startMinusMisStepAlongLine, mainAngle)
            misStepEndUpPoint = getMisStepPoint(true, misStep, endPlusMisStepAlongLine, mainAngle)
            misStepEndDownPoint =
                getMisStepPoint(false, misStep, endPlusMisStepAlongLine, mainAngle)

            drawLine(
                color = Color.Green,
                start = misStepStartUpPoint,
                end = misStepStartDownPoint,
                strokeWidth = 10f
            )

            drawLine(
                color = Color.Green,
                start = misStepEndUpPoint,
                end = misStepEndDownPoint,
                strokeWidth = 10f
            )

            for (miss in listOfMisses) {
                drawCircle(
                    color = Color.Black,
                    radius = 50f,
                    center = miss
                )
            }

            for (strike in listOfStrikes) {
                drawCircle(
                    color = Color.Green,
                    radius = 50f,
                    center = strike
                )
            }

            for (nearStart in listOfAroundStart) {
                drawCircle(
                    color = Color.Yellow,
                    radius = 20f,
                    center = nearStart
                )
            }

            for (nearEnd in listOfAroundEnd) {
                drawCircle(
                    color = Color.Blue,
                    radius = 20f,
                    center = nearEnd
                )
            }

            if (currentPointOnLine.x > endMainLinePoint.x
                && currentPointOnLine.y < endMainLinePoint.y
            ) {
                currentPointOnLine = Offset(endMainLinePoint.x, endMainLinePoint.y)

                isCurrentlyStrike = false
            }

            if (currentPointOnLine.x < startMainLinePoint.x
                && currentPointOnLine.y > startMainLinePoint.y
            ) {
                currentPointOnLine = Offset(startMainLinePoint.x, startMainLinePoint.y)

                isCurrentlyStrike = false
            }

/*            drawCircle(
                center = currentPointOnLine,
                color = Color.Red,
                radius = 60f
            )*/

            drawLine(
                color = Blue,
                start = startMainLinePoint,
                end = endMainLinePoint,
                strokeWidth = mainLineWidth
            )

            if (isCurrentlyStrike) {
                drawCircle(
                    center = outSidePointOnTheLine,
                    color = Color.Blue,
                    radius = 60f
                )

                drawLine(
                    color = Color.Black,
                    start = currentPointOnLine,
                    end = outSidePointOnTheLine,
                    strokeWidth = 20f
                )
            } else {
                drawCircle(
                    center = currentChosenPoint,
                    color = Color.Red,
                    radius = 60f
                )

                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "$currentChosenStep",
                        currentChosenPoint.x - 50,
                        currentChosenPoint.y + 50,
                        android.graphics.Paint().apply {
                            textSize = 40f
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier.size(100.dp)
        ) {
            Text(text = currentChosenStep.toString(), fontSize = 60.sp)
            Text(text = "Повторений")
        }
    }
}

private fun getNearestStep(
    mapOfSteps: Map<Int, Float>,
    longOnTheMainLine: Float,
    oneStep: Float
): Int {
    var result = 0
    for (i in 0..mapOfSteps.size) {
        val currentStep = mapOfSteps[i]
        if (currentStep != null) {
            val difference = longOnTheMainLine - currentStep
            if (difference < oneStep) {
                if (difference <= oneStep / 2.0) {
                    result = i
                } else {
                    result = i + 1
                }
                break
            }
        }
    }

    return result
}

private fun getMisStepPoint(
    up: Boolean,
    misStep: Float,
    point: Offset,
    mainLineAngle: Float
): Offset {
    val beta = PI / 2 - mainLineAngle
    val sin = sin(beta)
    val cos = cos(beta)

    val x = (misStep * cos).toFloat()
    val y = (misStep * sin).toFloat()

    val result = if (up) {
        Offset(point.x - x, point.y - y)
    } else {
        Offset(point.x + x, point.y + y)
    }

    return result
}

private fun getTouchAngle(
    touch: Offset,
    point: Offset,
): Float {
    val thirdPoint = Offset(touch.x, point.y)

    val prilejCathet = thirdPoint - point
    val prilejCathetLong = sqrt((prilejCathet.x * prilejCathet.x + prilejCathet.y * prilejCathet.y))

    val touchVector = touch - point
    val touchVectorLong = sqrt((touchVector.x * touchVector.x + touchVector.y * touchVector.y))

    val cosTouch = prilejCathetLong / touchVectorLong
    val touchAngle = acos(cosTouch)

    return touchAngle
}

private fun getTouchAngleInfo(
    touchOffset: Offset,
    startPoint: Offset,
    endPoint: Offset,
    alpha: Float,
    nearStart: Boolean
): TouchAngleRelativeStartEndInfo {
    val beta = PI / 2 - alpha

    val zeroHorizontal = if (nearStart) {
        startPoint.x - touchOffset.x == 0f
    } else {
        endPoint.x - touchOffset.x == 0f
    }

    val left = if (nearStart) {
        startPoint.x - touchOffset.x > 0f
    } else {
        endPoint.x - touchOffset.x > 0f
    }

    val zeroVertical = if (nearStart) {
        startPoint.y - touchOffset.y == 0f
    } else {
        endPoint.y - touchOffset.y == 0f
    }

    val up = if (nearStart) {
        startPoint.y - touchOffset.y > 0f
    } else {
        endPoint.y - touchOffset.y > 0f
    }


    val strike = isStrikeNearStartOrEnd(
        zeroHorizontal,
        zeroVertical,
        nearStart,
        touchOffset,
        startPoint,
        endPoint,
        beta,
        up,
        left
    )

    return TouchAngleRelativeStartEndInfo(
        strike,
        nearStart,
    )
}

private fun isStrikeNearStartOrEnd(
    zeroHorizontal: Boolean,
    zeroVertical: Boolean,
    nearStart: Boolean,
    touchOffset: Offset,
    startPoint: Offset,
    endPoint: Offset,
    beta: Double,
    up: Boolean,
    left: Boolean
): Boolean {
    val strike = if (!zeroHorizontal) {
        if (!zeroVertical) {
            val touchAngle = if (nearStart) {
                getTouchAngle(touchOffset, startPoint)
            } else {
                getTouchAngle(touchOffset, endPoint)
            }

            val betaGrad = beta * 57.3
            val startTouchGrad = touchAngle * 57.3

            if (nearStart) {
                if (up) {
                    if (left) {
                        touchAngle > beta
                    } else {
                        true
                    }
                } else {
                    if (left) {
                        false
                    } else {
                        touchAngle < beta
                    }
                }
            } else {
                if (up) {
                    if (left) {
                        touchAngle < beta
                    } else {
                        false
                    }
                } else {
                    if (left) {
                        true
                    } else {
                        touchAngle > beta
                    }
                }
            }
        } else {
            if (nearStart) {
                !left
            } else {
                left
            }
        }
    } else {
        if (nearStart) {
            startPoint.y - touchOffset.y >= 0
        } else {
            endPoint.y - touchOffset.y <= 0
        }
    }

    return strike
}

private fun isOnTheLine(
    touchOffset: Offset,
    mainLineVector: Offset,
    startPoint: Offset,
    endPoint: Offset,
    alpha: Float,
    misStepOutLine: Float,
    misStepAlongLine: Float
): IsOnTheLineInfo {

    val misStepAlongLineOffset = getMisStepAlongLineProectionOffset(misStepAlongLine, alpha)
    val startMinusMisStepAlongLine = startPoint - misStepAlongLineOffset
    val endPlusMisStepAlongLine = endPoint + misStepAlongLineOffset

    val relativeToMainLineInfo = getRelativeToMainLineInfo(touchOffset, mainLineVector, startPoint)

    val betweenStartAndEndInfo =
        beetweenEndAndStart(startMinusMisStepAlongLine, endPlusMisStepAlongLine, touchOffset)
    val betweenStartAndEndPoints = betweenStartAndEndInfo.beetween

    val shortLineLessThanMissStep = relativeToMainLineInfo.shortestLine <= misStepOutLine

    val nearStart =
        (startPoint.x - touchOffset.x).absoluteValue <= (endPoint.x - touchOffset.x).absoluteValue
                && (startPoint.y - touchOffset.y).absoluteValue <= (endPoint.y - touchOffset.y).absoluteValue

    var notBetweenButRightAngle = false
    if (shortLineLessThanMissStep && !betweenStartAndEndPoints) {
        val startEndInfo = getTouchAngleInfo(
            touchOffset,
            startMinusMisStepAlongLine,
            endPlusMisStepAlongLine,
            alpha,
            nearStart
        )
        notBetweenButRightAngle = startEndInfo.strike
    }

    val betweenAndShort = shortLineLessThanMissStep && betweenStartAndEndPoints
    val notBetweenAndShort = shortLineLessThanMissStep && notBetweenButRightAngle

    val isOnTheline = betweenAndShort || notBetweenAndShort

    return IsOnTheLineInfo(
        isOnTheline,
        relativeToMainLineInfo.distanceOnTheLine,
        nearStart
    )
}

private fun getRelativeToMainLineInfo(
    touchOffset: Offset,
    mainLineVector: Offset,
    startPoint: Offset,

    ): RelativeToMainLineInfo {
    val touchVector = touchOffset - startPoint

    val longTouchVector = sqrt(touchVector.x * touchVector.x + touchVector.y * touchVector.y)
    val longLineVector =
        sqrt(mainLineVector.x * mainLineVector.x + mainLineVector.y * mainLineVector.y)

    val scalarMultiplication = mainLineVector.x * touchVector.x + mainLineVector.y * touchVector.y
    var cosAlpha = scalarMultiplication / (longLineVector * longTouchVector)
    if (cosAlpha - 1 > 0 && cosAlpha - 1 < 0.1) {
        cosAlpha = 1f
    }
    val angleBetweenVectors = acos(cosAlpha)
    val sinAlpha = sin(angleBetweenVectors)

    val shortestLineFromTouchToMainLine = longTouchVector * sinAlpha
    val longTouchVectorProectionOnMainLine = longTouchVector * cosAlpha

    return RelativeToMainLineInfo(
        shortestLineFromTouchToMainLine,
        longTouchVectorProectionOnMainLine
    )
}

private fun getMisStepAlongLineProectionOffset(
    misStepAlongLine: Float,
    alpha: Float
): Offset {
    val x = misStepAlongLine * cos(alpha)
    val y = misStepAlongLine * sin(alpha)

    return Offset(x, -y)
}

private fun beetweenEndAndStart(
    startPoint: Offset,
    endPoint: Offset,
    touchOffset: Offset,
): BetweenInfo {
    val afterStart = touchOffset.x >= startPoint.x && touchOffset.y <= startPoint.y
    val beforeEnd = touchOffset.x <= endPoint.x && touchOffset.y >= endPoint.y

    val betweenStartAndEnd = afterStart && beforeEnd

    return BetweenInfo(
        betweenStartAndEnd
    )
}

private fun getPointOnLineProectionInfo(
    alpha: Float,
    longFromStartToPoint: Float,
    straightLongFromLine: Float
): PointOnLineProectionInfo {
    val sinAlpha = sin(alpha)
    val cosAlpha = cos(alpha)

    val xDiff = cosAlpha * longFromStartToPoint
    val yDiff = sinAlpha * longFromStartToPoint

    val beta = (PI / 2 - alpha).toFloat()
    val sinBeta = sin(beta)
    val cosBeta = cos(beta)

    val xOutSide = cosBeta * straightLongFromLine
    val yOutSide = sinBeta * straightLongFromLine

    return PointOnLineProectionInfo(
        Offset(xDiff, -yDiff),
        Offset(-xOutSide, -yOutSide)
    )
}

data class TouchAngleRelativeStartEndInfo(
    val strike: Boolean,
    val nearStart: Boolean,
)

data class IsOnTheLineInfo(
    val isOnTheLine: Boolean,
    val longOfTheTouchOnMainLine: Float,
    val nearStart: Boolean
)

data class PointOnLineProectionInfo(
    val pointOnLineOffset: Offset,
    val outSideBottomRightOffset: Offset
)

data class BetweenInfo(
    val beetween: Boolean,
)

data class RelativeToMainLineInfo(
    val shortestLine: Float,
    val distanceOnTheLine: Float
)

@Preview
@Composable
fun LineSilderPreview() {
    LineSlider()
}