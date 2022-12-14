package com.example.state.todo.two

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.state.ui.theme.ComposeTheme

/**
 * 顶部输入框部分的展开和折叠
 * 输入框中有文字时，自动展开图标
 */
class TodoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                TodoItemInput {
                    Log.d("TAGTAG", it.task)
                }
            }
        }
    }
}