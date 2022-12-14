package com.example.state.util

import com.example.state.todo.TodoIcon
import com.example.state.todo.TodoItem

/**
 * @author AlexisYin
 */
fun generateRandomTodoItem(): TodoItem{
    val message = listOf(
        "Learn Compose",
        "Learn state",
        "Build dynamic UIs",
        "Integrate LiveData",
        "Integrate ViewModel",
        "Remember to savedState",
        "Build stateless composable",
        "Use state from stateless composable"
    ).random()
    val icon = TodoIcon.values().random()
    return TodoItem(message, icon)
}