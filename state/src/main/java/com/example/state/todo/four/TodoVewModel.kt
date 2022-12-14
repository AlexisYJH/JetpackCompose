package com.example.state.todo.four

import android.text.TextUtils
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.state.todo.TodoItem

/**
 * @author AlexisYin
 * 编辑模式-3
 * ViewModel作为状态容器
 * mutableStateListOf
 */
class TodoVewModel : ViewModel() {
    //TodoItem集合只读
    var todoItems = mutableStateListOf<TodoItem>()
        private set

    //当前正在编辑的TodoItem的索引位置
    private var currentEditPostion by mutableStateOf(-1)

    //当前正在编辑的TodoItem对象
    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPostion)

    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
        onEditDone()
    }

    fun onEditDone() {
        currentEditPostion = -1
    }

    //当TodoItem列表中的条目被选中时，传入该对象，获取它的索引位置
    fun onEditItemSelected(item: TodoItem) {
        currentEditPostion = todoItems.indexOf(item)
    }

    //TodoItem编辑完成，重新给集合的TodoItem赋值
    //id属性值不能修改，进行校验
    fun onEditItemChange(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }
        todoItems[currentEditPostion] = item
    }
}