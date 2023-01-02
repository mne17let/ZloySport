package com.zloysport.ui.composables.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


private val itemWidth = 100.dp

private const val rowCount = 5

@Composable
fun <T> ListItemPicker(
    modifier: Modifier = Modifier,
    label: (T) -> String = { it.toString() },
    value: T,
    onValueChange: (T) -> Unit,
    list: List<T>,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    val rowWidthPx = with(LocalDensity.current) { itemWidth.toPx() }

    val coroutineScope = rememberCoroutineScope()

    val animatedOffset = remember { Animatable(0f) }.apply {
        val index = list.indexOf(value)
        val offsetRange = remember(value, list) {
            -((list.count() - 1) - index) * rowWidthPx to index * rowWidthPx
        }

        updateBounds(offsetRange.first, offsetRange.second)
    }
    val coercedAnimatedOffset = animatedOffset.value % rowWidthPx
    val currentIndex = getItemIndexForOffset(list, value, animatedOffset.value, rowWidthPx)

    Layout(
        modifier = modifier
            .draggable(orientation = Orientation.Horizontal,
                state = rememberDraggableState { deltaX ->
                    coroutineScope.launch {
                        animatedOffset.snapTo(animatedOffset.value + deltaX)
                    }
                },
                onDragStopped = { velocity ->
                    coroutineScope.launch {
                        val endValue = animatedOffset.fling(initialVelocity = velocity,
                            animationSpec = exponentialDecay(frictionMultiplier = 20f),
                            adjustTarget = { target ->
                                val coercedTarget = target % rowWidthPx
                                val coercedAnchors = listOf(-rowWidthPx, 0f, rowWidthPx)
                                val coercedPoint =
                                    coercedAnchors.minByOrNull { abs(it - coercedTarget) }!!
                                val base = rowWidthPx * (target / rowWidthPx).toInt()
                                coercedPoint + base
                            }).endState.value

                        val result = list.elementAt(
                            getItemIndexForOffset(list, value, endValue, rowWidthPx)
                        )
                        onValueChange(result)
                        animatedOffset.snapTo(0f)
                    }
                }
            ),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(itemWidth * rowCount)
                    .offset { IntOffset(x = coercedAnimatedOffset.roundToInt(), y = 0) }
            ) {
                val baseLabelModifier = Modifier.align(Alignment.Center)
                ProvideTextStyle(textStyle) {
                    for (index in (currentIndex - 3)..(currentIndex + 3)) {
                        if (index >= 0 && index < list.size) {
                            val off = index - currentIndex
                            val absOff = abs(off)
                            val half = rowWidthPx / 2f

                            when {
                                off == -3 && coercedAnimatedOffset > half -> {

                                    Label(
                                        text = label(list.elementAt(index)),
                                        modifier = baseLabelModifier
                                            .offset(x = itemWidth * off)
                                    )
                                }
                                off == -2 && ((coercedAnimatedOffset >= 0) || (coercedAnimatedOffset < 0 && coercedAnimatedOffset > -half)) -> {
                                    Label(
                                        text = label(list.elementAt(index)),
                                        modifier = baseLabelModifier
                                            .offset(x = itemWidth * off)
                                    )
                                }
                                off == 3 && coercedAnimatedOffset < -half -> {

                                    Label(
                                        text = label(list.elementAt(index)),
                                        modifier = baseLabelModifier
                                            .offset(x = itemWidth * off)
                                    )
                                }
                                off == 2 && ((coercedAnimatedOffset <= 0) || (coercedAnimatedOffset > 0 && coercedAnimatedOffset < half)) -> {

                                    Label(
                                        text = label(list.elementAt(index)),
                                        modifier = baseLabelModifier
                                            .offset(x = itemWidth * off)
                                    )
                                }
                                absOff == 1 -> {
                                    Label(
                                        text = label(list.elementAt(index)),
                                        modifier = baseLabelModifier
                                            .offset(x = itemWidth * off)
                                    )
                                }
                                off == 0 -> {
                                    Label(
                                        text = label(list.elementAt(index)),
                                        modifier = baseLabelModifier,
                                        color = MaterialTheme.colors.primary,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        layout(
            constraints.maxWidth,
            constraints.minHeight
        ) {
            var xPosition = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = xPosition, y = 0)
                xPosition += placeable.width
            }
        }
    }
}

@Composable
private fun Label(
    text: String,
    modifier: Modifier,
    color: Color = Color.Black,
) {
    Box(
        modifier = modifier.width(itemWidth)
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.Center),
//            color = color,
            fontSize = 38.sp
        )
    }
}

private suspend fun Animatable<Float, AnimationVector1D>.fling(
    initialVelocity: Float,
    animationSpec: DecayAnimationSpec<Float>,
    adjustTarget: ((Float) -> Float)?,
    block: (Animatable<Float, AnimationVector1D>.() -> Unit)? = null,
): AnimationResult<Float, AnimationVector1D> {
    val targetValue = animationSpec.calculateTargetValue(value, initialVelocity)
    val adjustedTarget = adjustTarget?.invoke(targetValue)
    return if (adjustedTarget != null) {
        animateTo(
            targetValue = adjustedTarget,
            initialVelocity = initialVelocity,
            block = block
        )
    } else {
        animateDecay(
            initialVelocity = initialVelocity,
            animationSpec = animationSpec,
            block = block,
        )
    }
}

private fun <T> getItemIndexForOffset(
    range: List<T>, value: T, offset: Float, halfNumbersColumnHeightPx: Float
): Int {
    val indexOf = range.indexOf(value) - (offset / halfNumbersColumnHeightPx).toInt()
    return maxOf(0, minOf(indexOf, range.count() - 1))
}