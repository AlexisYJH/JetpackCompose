package com.example.state.todo.three

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.state.todo.TodoItem
import com.example.state.util.generateRandomTodoItem
import java.util.*

/**
 * @author AlexisYin
 */
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Column {
        // 输入框，外加一个灰色背景
        TodoItemInputBackground(elevate = true) {
            TodoItemEntryInput(onItemComplete = onAddItem)
        }
        //多行
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items) {
                TodoRow(
                    todo = it,
                    onItemClick = { item -> onRemoveItem(item) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }
        Button(
            onClick = {
                onAddItem(generateRandomTodoItem())
            }, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Add random item")
        }
    }
}

@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onItemClick(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = todo.task)
        //为了保证再次重组时透明度不变
        val iconAlpha: Float = remember(todo.id) {
            randomTint()
        }
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float {
    return Random().nextFloat().coerceIn(0.3f, 0.9f)
}