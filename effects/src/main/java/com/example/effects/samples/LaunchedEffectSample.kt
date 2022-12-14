package com.example.effects.samples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * @author AlexisYin
 * LaunchedEffect用于创建协程。
 * - 当LaunchedEffect进入组件树，会启动一个协程，把将block放入该协程执行。
 * - 当组合函数从树上detach时，协程还未被执行完毕，该协程**也会被取消执行**。
 * - 当LaunchedEffect在重组时key不变，那LaunchedEffect不会被重新启动执行block.
 * - 当LaunchedEffect在重组时key变了，则LaunchedEffect会执行cancel后，再**重新启动一个新协程执行block**.
 */
@Composable
fun ScaffoldSample(
    state: MutableState<Boolean>,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    if (state.value) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                "Error message", "Retry message"
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = "脚手架示例") })
        },
        content = {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    state.value = !state.value
                }) {
                    Text(text = "Error occurs")
                }
            }
        }
    )
}


@Composable
fun LaunchedEffectSample() {
    val state = remember {
        mutableStateOf(false)
    }
    ScaffoldSample(state)
}