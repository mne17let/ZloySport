package com.zloysport.ui.composables

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ScreenTimer(
    relaxTime: Long,
    navController: NavController
) {
    val viewModel = viewModel<TimerViewModel>()

    val timerEnd = remember { viewModel.timerEnd }

    LaunchedEffect(key1 = timerEnd.value, block = {
        if (timerEnd.value) {
            navController.navigate("drill") {
                popUpTo("timer") {
                    inclusive = true
                }
            }

            timerEnd.value = false
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Timer(
            viewModel
        )
        Buttons()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun Timer(
    viewModel: TimerViewModel
) {

    Log.d(TAG, "Timer: рекомпозиция таймера")

    val count = remember { viewModel.count }
    val animatedTime = animateFloatAsState(targetValue = count.value.toFloat())

    val time = remember { viewModel.text }

    val coroutineScope = rememberCoroutineScope()

    var screenWidth by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .size(300.dp)
            .clickable {

            },
        contentAlignment = Alignment.Center
    ) {
        Log.d(TAG, "Box")
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Log.d(TAG, "канвас")

            drawCircle(
                color = Color.Green,
                style = Stroke(
                    width = 24f
                )
            )

            drawArc(
                color = Color.Black,
                startAngle = 0f,
                sweepAngle = animatedTime.value,
                useCenter = false,
                topLeft = Offset(size.width / 2 - size.minDimension / 2, size.height / 2 - size.minDimension / 2),
                size = Size(size.minDimension, size.minDimension),
                style = Stroke(
                    width = 24f
                )
            )
        }

        Text(time.value)
    }
}

@Composable
private fun Buttons() {

}

class TimerViewModel(
    val time: Long = 2000
): ViewModel() {
    var timeLeft = 360
    var count = mutableStateOf(360.0)
    var text = mutableStateOf("")

    val looper = Looper.getMainLooper()
    val handler = Handler(looper)

    var helpTime = time


    val hours = (((time / 1000) / 60) / 60)
    val minutes = (((time / 1000) / 60) % 60)
    val seconds = (((time / 1000) % 60) % 60)

    val timeInSeconds = time / 100

    val oneAngle = 360.0 / timeInSeconds

    var timerEnd = mutableStateOf(false)

    init {

        val thread = object : Thread() {
            override fun run() {

                var useMinus = true
                for (angle in 0 until timeInSeconds) {
                    Thread.sleep(100)
                    handler.post {
                        if (useMinus) {
                            count.value -= oneAngle
                        } else {
                            count.value += oneAngle
                        }

                        if (count.value == 0.0 || count.value == 360.0) {
                            useMinus = !useMinus
                        }
                        helpTime -= 100

                        val helpHours = (((helpTime / 1000) / 60) / 60)
                        val helpMinutes = (((helpTime / 1000) / 60) % 60)
                        val helpSeconds = (((helpTime / 1000) % 60) % 60)

                        text.value = "Сумма == ${count.value} || Часы = $helpHours || минуты = $helpMinutes || секунды == $helpSeconds || время в секундах == $helpTime"

//                        handler.post {
//
//                        }
                    }
                }

                timerEnd.value = true
            }
        }

        thread.start()
    }
}

private const val TAG = "LOGTAG"