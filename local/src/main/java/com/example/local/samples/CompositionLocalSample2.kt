package com.example.local.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.local.R

/**
 * @author AlexisYin
 * 为CompositionLocal提供新值，使用CompositionLocalProvider及其**provides infix**函数
 * CompositionLocal的**current**值对应该组合部分中某个祖先提供的最接近的值
 */
@Composable
fun Sample2() {
    MaterialTheme {
        Column {
            Text("Uses MaterialTheme's provider alpha")
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("Medium value provided for LocalContentAlpha")
                Text("This Text also uses the medium value")
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                    DescendantText()
                }
            }
            FruitText(2)
        }
    }
}

@Composable
fun DescendantText() {
    Text(
        "This Text uses the disabled alpha now"
    )
}

@Composable
fun FruitText(fruitSize: Int) {
    val resources = LocalContext.current.resources
    val fruitText = resources.getQuantityString(R.plurals.fruit_title, fruitSize)
    Text(text = "$fruitSize $fruitText")
}