package com.example.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.example.layout.ui.theme.ComposeTheme


/**
 * @author AlexisYin
 * 自定义布局2
 */
@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        //测量元素
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var yPosition = 0
        //布局大小
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun MyOwnColumnSample() {
    ComposeTheme {
        MyOwnColumn (Modifier.padding(8.dp)){
            Text(text = "MyOwnColumn")
            Text(text = "place items")
            Text(text = "vertically.")
            Text(text = "We've done it by hand!")
        }
    }
}