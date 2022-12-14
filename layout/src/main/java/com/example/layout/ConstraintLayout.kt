package com.example.layout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

/**
 * @author AlexisYin
 * ConstraintLayout
 */
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        //通过createRefs创建引用，ConstraintLayout中的每一个元素都需要关联一个引用
        val (button, text) = createRefs()
        Button(onClick = { },
            //使用Modifier.constrainAs来提供约束，引用作为它的第一个参数
            modifier = Modifier.constrainAs(button) {
                //使用linkTo指定约束
                top.linkTo(parent.top, margin = 16.dp)
            }) {
            Text(text = "Button")
        }

        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
    }
}

@Composable
fun ConstraintLayoutContent2() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()
        Button(onClick = {}, modifier = Modifier.constrainAs(button1) {
            top.linkTo(parent.top, margin = 16.dp)
        }) {
            Text(text = "Button 1")
        }

        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        //将button1和text组合起来，建立一个屏障
        val barrier = createEndBarrier(button1, text)
        Button(onClick = {}, modifier = Modifier.constrainAs(button2) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(barrier)
        }) {
            Text(text = "Button 2")
        }
    }
}

@Composable
fun LargeLayoutContent() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            text = "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}

//把margin抽出来
@Composable
fun DecoupledConstraintLayout() {
    val margin = 16.dp
    ConstraintLayout {
        val (button, text) = createRefs()
        Button(onClick = { },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = margin)
            }) {
            Text(text = "Button")
        }

        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = margin)
        })
    }
}

//横竖屏不同margin
@Composable
fun DecoupledConstraintLayout2() {
    BoxWithConstraints {
        val constraints = if(maxWidth < maxHeight) {
            decoupledConstraints(16.dp)
        } else {
            decoupledConstraints(160.dp)
        }
        //1. 将ConstraintSet作为参数传递给ConstraintLayout
        //2. 使用layoutId修饰符将ConstraintSet中创建的引用分配给可组合项
        ConstraintLayout (constraints){
            Button(onClick = { },
                modifier = Modifier.layoutId("button")) {
                Text(text = "Button")
            }

            Text(text = "Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp) : ConstraintSet {
    return ConstraintSet{
        val button = createRefFor("button")
        val text = createRefFor("text")
        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin = margin)
        }
    }
}
