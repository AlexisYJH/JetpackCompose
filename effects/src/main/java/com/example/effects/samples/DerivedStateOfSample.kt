package com.example.effects.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * @author AlexisYin
 * derivedStateOf: 从其他状态对象计算或派生。当任意条件状态更新时，结果状态都会重新计算
 */
@Composable
fun DerivedStateOfSample() {
    TodoList()
}

fun String.containsWord(list : List<String>): Boolean{
    for (item in list) {
        if (this.contains(item)) return true
    }
    return false
}

@Composable
fun TodoList(highPriorityWords: List<String> = listOf("Review", "Compose")) {
    val todoTasks = remember {
        mutableStateListOf<String>()
    }
    val highPriorityTasks = remember(todoTasks, highPriorityWords) {
        derivedStateOf {
            todoTasks.filter { it.containsWord(highPriorityWords) }
        }
    }

    var (text, setText) = remember {
        mutableStateOf("")
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = text,
                onValueChange = setText,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            )
            Button(
                onClick = {
                    todoTasks.add(text)
                    setText("")
                }) {
                Text(text = "Add")
            }
        }

        LazyColumn {
            items(highPriorityTasks.value) {
                ItemText(text =it, modifier = Modifier.background(Color.LightGray))
            }
            items(todoTasks) {
                ItemText(text =it)
            }
        }
    }
}

@Composable
fun ItemText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, textAlign = TextAlign.Center, modifier = modifier
            .height(30.dp)
            .fillMaxWidth()
    )
}
