package com.example.effects.samples

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*

/**
 * @author AlexisYin
 * DisposedEffect
 * DisposedEffect也是可组合函数，当DisposedEffect在其key值变化或组合函数离开组件树时，会取消之前启动的协程，
 * **并在取消协程前调用其回收方法进行资源回收相关的操作**。
 */
@Composable
fun DisposedEffectSample(backDispatcher: OnBackPressedDispatcher) {
    var addBackCallback by remember {
        mutableStateOf(false)
    }

    Row {
        Switch(checked = addBackCallback, onCheckedChange = {
            addBackCallback = !addBackCallback
        })
        Text(text = if (addBackCallback) "Add Back Callback" else "Not Add Back Callback")
    }
    if (addBackCallback) {
        BackHandler(backDispatcher) {
            Log.d("TAG", "onBack")
        }
    }
}

@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher,
    onBack: () -> Unit
) {
    val currentOnBack by rememberUpdatedState(onBack)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }

    DisposableEffect(backDispatcher) {
        backDispatcher.addCallback(backCallback)
        onDispose {
            Log.d("TAG", "onDispose")
            backCallback.remove()
        }
    }
}