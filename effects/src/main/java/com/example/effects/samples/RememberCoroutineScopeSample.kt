package com.example.effects.samples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

/**
 * @author AlexisYin
 * rememberCoroutineScope
 * - 由于LaunchedEffect是可组合函数，只能在其他可组合函数中使用。想要**在可组合项外启动协程**，
 * 且需要对这个协程存在作用域限制，以便**协程在退出组合后自动取消**，可以使用rememberCoroutineScope。
 * - 需要**手动控制一个或多个协程的生命周期**，使用rememberCoroutineScope。
 *
 */
@Composable
fun ScaffoldSample() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = "抽屉组件中的内容")
            }  
        },
        //标题栏区域
        topBar = {
            TopAppBar(
                title = { Text(text = "脚手架示例") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                    }
                })
        },
        //屏幕内容区域
        content = {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("屏幕内容区域")
            }
        },
        //悬浮按钮
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "悬浮按钮") },
                onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("点击了悬浮按钮")
                    }
                }
            )
        }
    )
}
