package com.zloysport.ui.composables

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun ScreenTimer(
    relaxTime: Long,
    navController: NavController
) {
    val viewModel = viewModel<TimerViewModel>()

    val timerEnd = remember { viewModel.timerEnd }

    val createServiceAgain = remember { viewModel.createServiceAgain }

    Log.d(TAG, "Рекомпозиция экрана")

    LaunchedEffect(key1 = timerEnd.value, block = {
        Log.d(TAG, "LaunchedEffect с НАВИГАЦИЕЙ запущен || таймер закончился == ${timerEnd.value}")

        if (timerEnd.value) {
            Log.d(TAG, "навигация")

            delay(500)

            navController.navigate("drill") {
                popUpTo("timer") {
                    inclusive = true
                }
            }

            timerEnd.value = false
        }
    })

    val context = LocalContext.current
    LaunchedEffect(createServiceAgain.value) {
        Log.d(TAG, "LaunchedEffect с СЕРВИСОМ запущен || Значение КЛЮЧА == ${createServiceAgain.value}")
        if (createServiceAgain.value) {
            val intent = Intent(context, TimerService::class.java)
            context.bindService(intent, ConnectProvider(viewModel), Context.BIND_AUTO_CREATE)

            createServiceAgain.value = false
        }
    }


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

class ConnectProvider(val timerViewModel: TimerViewModel): ServiceConnection{
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d(TAG, "onServiceConnected: Вызван онСервисКоннектед")
        val timerBinder = service as TimerService.TimerBinder
        timerBinder.setViewModel(timerViewModel)
        timerBinder.startCmd()
    }

    override fun onServiceDisconnected(name: ComponentName?) {

    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun Timer(
    viewModel: TimerViewModel
) {

//    Log.d(TAG, "Timer: рекомпозиция таймера")

    val count = remember { viewModel.count }
    val animatedTime = animateFloatAsState(targetValue = count.value.toFloat())

    val time = remember { viewModel.text }

    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
//        Log.d(TAG, "Box")
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
//            Log.d(TAG, "канвас")

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
//                sweepAngle = count.value.toFloat(),
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

): ViewModel() {
    var count = mutableStateOf(360.0)
    var text = mutableStateOf("")
    var timerEnd = mutableStateOf(false)
    var createServiceAgain = mutableStateOf(true)
}

private const val TAG = "LOGTAG"

class TimerService(
    val time: Long = 2000
): Service() {
    lateinit var timerViewModel: TimerViewModel
    var timeLeft = 360

    val looper = Looper.getMainLooper()
    val handler = Handler(looper)

    var helpTime = time


    val hours = (((time / 1000) / 60) / 60)
    val minutes = (((time / 1000) / 60) % 60)
    val seconds = (((time / 1000) % 60) % 60)

    val timeInSeconds = time / 100

    val oneAngle = 360.0 / timeInSeconds

    init {
        Log.d(TAG, "Сервис создан $this")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: Сервис стартовал команду $this")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind: Вызван онБайнд у сервиса $this")
        return TimerBinder()
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate: Вызван онКриэйт у сервиса $this")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: Вызван онДестрой у сервиса $this")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: Вызван онАнБайнд у сервиса $this")

        return super.onUnbind(intent)
    }

    inner class TimerBinder: Binder() {
        fun setViewModel(newViewModel: TimerViewModel) {
            Log.d(TAG, "setViewModel: Вызван сет вьюмодел $this")
            timerViewModel = newViewModel
        }

        fun startCmd() {
            Log.d(TAG, "startCmd: стартовала команда у сервиса $this")

            val thread = object : Thread() {
                override fun run() {
                    for (angle in 0 until timeInSeconds - 1) {
                        Thread.sleep(100)
                        handler.post {
                            timerViewModel.count.value -= oneAngle

                            helpTime -= 100

                            val helpHours = (((helpTime / 1000) / 60) / 60)
                            val helpMinutes = (((helpTime / 1000) / 60) % 60)
                            val helpSeconds = (((helpTime / 1000) % 60) % 60)

                            timerViewModel.text.value = "Сумма == ${timerViewModel.count.value} || Часы = $helpHours || минуты = $helpMinutes || секунды == $helpSeconds || время в секундах == $helpTime"
                        }
                    }

                    timerViewModel.timerEnd.value = true
                }
            }

            thread.start()
        }
    }
}