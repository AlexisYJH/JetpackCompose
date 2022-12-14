package com.example.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author AlexisYin
 */
@Composable
fun TwoTexts(modifier: Modifier = Modifier) {

    /*fun Modifier.height(intrinsicSize: IntrinsicSize) = when (intrinsicSize) {
        IntrinsicSize.Min -> this.then(MinIntrinsicHeightModifier)
        IntrinsicSize.Max -> this.then(MaxIntrinsicHeightModifier)
    }*/

    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            text = "Hi", modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start)
        )

        Divider(color = Color.Black, modifier = Modifier
            .fillMaxHeight()
            .width(1.dp))

        Text(
            text = "there", modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End)
        )
    }
}