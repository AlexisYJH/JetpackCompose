package com.example.local.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.local.R

/**
 * @author AlexisYin
 * 使用自定义CompositionLocal
 */
@Composable
fun Sample3() {
    Column {
        CompositionLocalProvider(LocalElevations provides CardElevation.high) {
            MyCard(backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f)) {

            }
        }
        MyCard(backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f)) {

        }
    }

}