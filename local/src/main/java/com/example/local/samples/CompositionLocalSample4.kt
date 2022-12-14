package com.example.local.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author AlexisYin
 * compositionLocalOf vs staticCompositionLocalOf
 * - compositionLocalOf，如果更改提供的值，会使读取其current值的组件发生重组。（只有Middle重组）
 * - staticCompositionLocalOf，如果更改值，会使提供CompositionLocal的**整个content lambda被重组**。（3个TaggedBox组件都重组）
 */

var isStatic = false
var compositionLocalName = ""
val currentLocalColor = if (isStatic) {
    compositionLocalName = "StaticCompositionLocal 场景"
    staticCompositionLocalOf { Color.Black }
} else {
    compositionLocalName = "CompositionLocal 场景"
    compositionLocalOf { Color.Black }
}

//重组标记。组件首次加载，recomposeFlag为Init；重组之前，将recomposeFlag设置为Recompose
var recomposeFlag = "Init"

@Composable
fun Sample4() {
    val (color, setColor) = remember {
        mutableStateOf(Color.Green)
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = compositionLocalName)
            Spacer(modifier = Modifier.height(20.dp))
            //3个TaggedBox组件，只有Middle使用了CompositionLocal
            CompositionLocalProvider(currentLocalColor provides color) {
                TaggedBox("Wrapper: $recomposeFlag", 400.dp, Color.Red) {
                    TaggedBox("Middle: $recomposeFlag", 300.dp, currentLocalColor.current) {
                        TaggedBox("Inner: $recomposeFlag", 200.dp, Color.Yellow)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            //点击按钮改变状态，将颜色设置为蓝色，观察3个TaggedBox组件是否重组
            Button(onClick = {
                setColor(Color.Blue)
                recomposeFlag = "Recompose"
            }) {
                Text(text = "Change Theme")
            }
        }
    }
}

@Composable
fun TaggedBox(
    tag: String,
    size: Dp,
    background: Color,
    content: @Composable () -> Unit = { }
) {
    Column(
        modifier = Modifier
            .size(size = size)
            .background(color = background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = tag)
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}