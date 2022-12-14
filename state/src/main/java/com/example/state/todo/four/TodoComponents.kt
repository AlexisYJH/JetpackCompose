package com.example.state.todo.four

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.state.todo.TodoIcon
import com.example.state.todo.TodoItem

/**
 * @author AlexisYin
 * 编辑模式-1：
 * 1. TodoItemInput中抽取buttonSlot
 * 2. TodoItemInlineEditor
 */
//当TodoItem列表中的条目被选中时，会弹出一个输入框，用于编辑选中TodoItem的信息
@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) {
    TodoItemInput(
        text = item.task,
        onTextChange = { onEditItemChange(item.copy(task = it)) },
        icon = item.icon,
        onIconChange = { onEditItemChange(item.copy(icon = it)) },
        submit = onEditDone,
        iconVisible = true,
        buttonSlot = {
            //保存和删除两个图标
            Row{
                val shrinkButtons = Modifier.widthIn(20.dp)
                TextButton(onClick = onEditDone, modifier = shrinkButtons, enabled = item.task.isNotBlank()) {
                    Text(
                        text = "\uD83D\uDCBE", //Emoji符号，软盘
                        textAlign = TextAlign.End
                    )
                }
                TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                    Text(
                        text = "✖",
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    )
}

//顶部输入框加上一个灰色背景
@Composable
fun TodoItemInputBackground(
    elevate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    //帧动画的形式展现Surface底部的阴影
    val animatedElevation by animateDpAsState(
        if (elevate) 1.dp else 0.dp,
        TweenSpec(300)
    )

    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
        shape = RectangleShape,
        //Surface底部有一个小小的阴影
        elevation = animatedElevation
    ) {
        Row(
            modifier = modifier.animateContentSize(animationSpec = TweenSpec(300)),
            content = content
        )
    }
}

//输入框

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TodoInputText(
    text: String,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text, onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = 1, modifier = modifier,
        //配置软键盘
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        })
    )
}

//按钮
@Composable
fun TodoEditButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text = text)
    }
}

//一排图标，根据文本框是否有内容，自动收起和弹出，带动画效果

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true
) {
    val enter = remember {
        fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing))
    }
    val exit = remember {
        fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing))
    }
    Box(modifier.defaultMinSize(minHeight = 16.dp)) {
        AnimatedVisibility(visible = visible, enter = enter, exit = exit) {
            IconRow(icon = icon, onIconChange = onIconChange)
        }
    }
}

@Composable
fun IconRow(icon: TodoIcon, onIconChange: (TodoIcon) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier) {
        for (todoIcon in TodoIcon.values()) {
            SelectableIconButton(
                icon = todoIcon.imageVector,
                iconContentDescription = todoIcon.contentDescription,
                onIconSelected = { onIconChange(todoIcon) },
                isSelected = (todoIcon == icon)
            )
        }
    }
}

@Composable
fun SelectableIconButton(
    icon: ImageVector,
    iconContentDescription: Int,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    //图标选中和未选中状态，颜色不一样
    val tint = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }
    TextButton(onClick = onIconSelected, shape = CircleShape, modifier = modifier) {
        Column {
            Icon(
                imageVector = icon,
                tint = tint,
                contentDescription = stringResource(id = iconContentDescription)
            )
            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    //这个函数使用remember给自己添加内存，然后在内存中存储一个由mutableStateOf创建的MutableState<String>,
    //它是Compose的内置类型，提供一个可观察的状态持有者。
    //val (value, setValue) = remember {mutableStateOf(default)}
    //对value的任何更改都将自动重新组合读取此状态的任何可组合函数。
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    //IconRow是否可见取决于文本框中是否有内容
    val iconVisible = text.isNotBlank()

    //点击"Add"按钮，提交要做的事情
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }
    TodoItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconVisible = iconVisible
    ) {
        TodoEditButton(
            onClick = submit,
            text = "Add",
            //modifier = Modifier.align(Alignment.CenterVertically),
            enabled = text.isNotBlank()
        )
    }
}

@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconVisible: Boolean,
    //最右侧图标与按钮，在编辑时，显示删除和保存两个图标，添加时显示"Add"按钮
    buttonSlot: @Composable () -> Unit
) {


    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                onImeAction = submit,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            /*TodoEditButton(
                onClick = submit,
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank()
            )*/
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                buttonSlot()
            }
        }
        if (iconVisible) {
            AnimatedIconRow(
                icon = icon,
                onIconChange = onIconChange,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}