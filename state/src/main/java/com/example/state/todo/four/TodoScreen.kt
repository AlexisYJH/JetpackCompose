package com.example.state.todo.four

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.state.todo.TodoItem
import com.example.state.util.generateRandomTodoItem
import java.util.*

/**
 * @author AlexisYin
 * 编辑模式-2：修改条目点击时进入编辑状态
 * 1. TodoScreen增加currentEditing, onStartEdit, onEditItemChange, onEditDone
 * 2. 顶部，正常状态显示TodoItemEntryInput，编辑状态显示Text
 * 3. 条目，正常状态显示TodoRow，编辑状态显示TodoItemInlineEditor
 * 4. 底部，编辑状态enable=false
 */
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    currentEditing: TodoItem?,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    onStartEdit: (TodoItem) -> Unit,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
) {
    Column {
        // 当currentEditing（当前编辑条目）为空时，显示添加输入框
        val enableTopSection = (currentEditing == null)
        // 输入框，外加一个灰色背景
        TodoItemInputBackground(elevate = true) {
            if (enableTopSection) {
                TodoItemEntryInput(onItemComplete = onAddItem)
            } else {
                Text(
                    text = "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
        //多行
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items) {todo->
                if (currentEditing?.id == todo.id) {
                    TodoItemInlineEditor(
                        item = currentEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemoveItem = {onRemoveItem(todo)}
                    )
                } else {
                    TodoRow(
                        todo = todo,
                        onItemClick = { onStartEdit(todo) },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
        Button(
            onClick = {
                onAddItem(generateRandomTodoItem())
            }, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            enabled = enableTopSection
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