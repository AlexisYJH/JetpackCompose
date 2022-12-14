package com.example.state.todo.three

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.state.todo.TodoItem
import com.example.state.todo.one.TodoVewModel
import com.example.state.ui.theme.ComposeTheme

/**
 * 输入添加，显示到列表
 * 点击条目删除
 */
class TodoActivity : ComponentActivity() {
    private val todoViewModel by viewModels<TodoVewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                TodoActivityScreen()
            }
        }
    }

    @Composable
    private fun TodoActivityScreen() {
        val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())
        TodoScreen(items,
            onAddItem = { todoViewModel.addItem(it) },
            onRemoveItem = { todoViewModel.removeItem(it) })
    }
}