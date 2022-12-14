package com.example.local.samples

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

/**
 * @author AlexisYin
 * MaterialTheme的color、shapes和typography属性访问LocalColor、LocalShapes和LocalTypography属性。
 */
@Composable
fun Sample1() {
    MaterialTheme {
        CustomTextLabel(text = "Some Composable deep in the hierarchy of MaterialTheme")
    }
}

@Composable
fun CustomTextLabel(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary
    )

}