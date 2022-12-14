package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

/**
 * 布局
 */
class MainActivity02 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(Message("Android", "Jetpack Compose"))
        }
    }
    
    @Composable
    fun MessageCard(msg: Message) {
        Row {
            Image(painter = painterResource(id = R.mipmap.ic_photo), contentDescription = null)
            Column {
                Text(text = msg.author)
                Text(text = msg.body)
            }
        }
    }
}