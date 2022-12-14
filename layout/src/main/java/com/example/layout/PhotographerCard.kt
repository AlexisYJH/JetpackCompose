package com.example.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * @author AlexisYin
 * 标准布局组件，修饰符
 *
 */
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.surface)
            //修饰符的顺序
            //.clickable(onClick = {}).padding(16.dp)
            .padding(16.dp).clickable(onClick = {})
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            Image(painter = painterResource(id = R.mipmap.ic_photo), contentDescription = null)
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Lil Wen", fontWeight = FontWeight.Bold)
            //infix fun provides(value: T)
            //val LocalContentAlpha = compositionLocalOf { 1f }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }


}