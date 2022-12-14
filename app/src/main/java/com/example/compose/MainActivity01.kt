package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Composable函数与预览
 */
class MainActivity01 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //实际显示
            MessageCard(name = "My Android")
        }
    }
    
    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name")
    }

    //Preview函数不能带参数
    @Preview
    @Composable
    fun PreviewMessageCard() {
        MessageCard(name = "Android!")
    }
}