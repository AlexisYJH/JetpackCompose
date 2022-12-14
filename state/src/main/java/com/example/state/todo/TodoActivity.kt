package com.example.state.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.state.todo.one.TodoRow
import com.example.state.ui.theme.ComposeTheme

/**
 * 静态页面
 */
class TodoActivity : ComponentActivity() {
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
        val items = listOf(
            TodoItem("Learn compose", TodoIcon.Event),
            TodoItem("Take the code lab"),
            TodoItem("Apply state", TodoIcon.Done),
            TodoItem("Build dynamic UIs", TodoIcon.Square)
        )
        TodoScreen(items)
    }

    @Composable
    fun TodoScreen(items: List<TodoItem>) {
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(top = 8.dp)
            ) {
                items(items) {
                    TodoRow(
                        todo = it,
                        onItemClick = {},
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
            Button(
                onClick = {}, modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add random item")
            }
        }
    }
}