package com.example.effects.samples

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

/**
 * @author AlexisYin
 * rememberUpdatedState
 * 给某个参数创建一个引用，来跟踪这些参数，**并保证其值被使用时是最新值，参数被改变时不重启effect**
 */
@Composable
fun RememberUpdatedStateSample() {
    val onTimeOut1: ()-> Unit = { Log.d("TAG", "Landing timeout 1.")}
    val onTimeOut2: ()-> Unit = { Log.d("TAG", "Landing timeout 2.")}

    val changeOnTimeOutState = remember {
        mutableStateOf(onTimeOut1)
    }

    Column {
        Button(onClick = {
            if(changeOnTimeOutState.value == onTimeOut1) {
                changeOnTimeOutState.value = onTimeOut2
            } else {
                changeOnTimeOutState.value = onTimeOut1
            }
        }) {
            Text(text = "choose onTimeOut${if(changeOnTimeOutState.value == onTimeOut1) 1 else 2}")
        }
        LandingScreen(changeOnTimeOutState.value)
    }
}

@Composable
fun LandingScreen(onTimeOut: ()-> Unit) {
    val currentTimeOut by rememberUpdatedState(onTimeOut)
    LaunchedEffect(Unit){
        Log.d("TAG", "LandingScreen")
        repeat(10) {
            delay(1000)
            Log.d("TAG", "delay ${it+1} s")
        }
        currentTimeOut()
    }
}