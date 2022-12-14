package com.example.layout

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.layout.ui.theme.ComposeTheme

/**
 * @author AlexisYin
 * 自定义布局1
 */

fun Modifier.firstBaselineToTop (firstBaselineToTop: Dp)
= this.then(
    layout{measurable, constraints ->
        //测量元素
        val placeable = measurable.measure(constraints)
        //测量之后，获取元素的基准线
        val firstBaseline = placeable[FirstBaseline]
        //元素新的Y坐标 = 新基线 - 旧基线
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun TextWithPaddingToBaseline() {
    ComposeTheme {
        Text(text = "Hi there!",
        Modifier.firstBaselineToTop(24.dp).background(Color.Gray))
    }
}