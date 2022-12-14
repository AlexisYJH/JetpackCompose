package com.example.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

/**
 * @author AlexisYin
 * 自定义布局3
 */

val topics = listOf(
    "1 Arts & Crafts",
    "2 Beauty",
    "3 Books",
    "4 Business",
    "5 Comics",
    "6 Culinary",
    "7 Design",
    "8 Fashion",
    "9 Film",
    "10 History",
    "11 Maths"
)

@SuppressLint("NewApi")
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable ()->Unit
) {
    Layout(modifier = modifier,
    content = content) {measurables, constraints ->
        //用于保存每行的宽高
        val rowWidths = IntArray(rows){0}
        val rowHeights = IntArray(rows){0}
        val placeables = measurables.mapIndexed { index, measurable ->

            //测量每个元素
            val placeable = measurable.measure(constraints)
            //计算每一行的宽度和高度
            //元素下标，假设11个元素
            //index: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            //行数，假设3行
            //rows: 3
            //保存行宽高数组的下标值
            //row: 0, 1, 2
            val row = index % rows
            //一行的宽度 = 所有元素宽度之和
            rowWidths[row] += placeable.width
            //一行的高度 = 一行中高度最大的元素的高度
            rowHeights[row] = max(rowHeights[row], placeable.height)
            placeable
        }

        //计算表格的宽高
        //表格的宽度 = 所有行中最宽的那一行的宽度
        val width = rowWidths.maxOrNull() ?: constraints.minWidth
        //表格的高度 = 所有行高度之和
        val height = rowHeights.sumOf { it }

        //设置每一行的Y坐标
        val rowY = IntArray(rows){0}
        for (i in 1 until rows) {
            rowY[i] = rowY[i-1] + rowHeights[i-1]
        }
        layout(width, height) {
            val rowX = IntArray(rows){0}
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(rowX[row], rowY[row])
                //第一列，x坐标全部为0，下一列的x坐标加上前面元素的宽度
                //设置下一列的x坐标
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String
) {
    //一个卡片，圆角，里面包含一个Row，第一列是Box，第二列是文本
    Card(modifier = modifier,
        border = BorderStroke(width = Dp.Hairline, color = Color.Black),
        shape = RoundedCornerShape(8.dp)
        ) {
        Row(modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Composable
fun StaggeredGridBodyContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            .padding(16.dp)
            .horizontalScroll(
                rememberScrollState()
            ),
        content = {
            StaggeredGrid (modifier = Modifier){
                for(topic in topics) {
                    Chip(text = topic, modifier = Modifier.padding(8.dp))
                }
            }
        }

    )

}