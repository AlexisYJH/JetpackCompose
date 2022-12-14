package com.example.gesture

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.gesture.ui.theme.ComposeTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                GestureSample()
            }
        }
    }
}

@Composable
fun GestureSample() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TransformableSample()
    }

}

@Composable
fun ClickableSample() {
    val count = remember { mutableStateOf(0) }
    Text(
        text = count.value.toString(),
        textAlign = TextAlign.Center,
        modifier = Modifier
            //.clickable { count.value += 1 }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { Log.d("TAG", "Called onDoubleTap") },
                    onLongPress = { Log.d("TAG", "Called onLongPress") },
                    onPress = { Log.d("TAG", "Called onPress") },
                    onTap = { Log.d("TAG", "Called onTap") }
                )
            }
            .wrapContentSize()
            .background(Color.LightGray)
            .padding(horizontal = 50.dp, vertical = 40.dp)
    )
}

@Composable
fun ScrollSample() {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        repeat(10) {
            Text(text = "Item $it", modifier = Modifier.padding(5.dp))
        }
    }
}

//借助ScrollState，更改滚动位置或获取当前状态
@Composable
fun ScrollSample2() {
    val state = rememberScrollState()
    LaunchedEffect(Unit) {
        state.animateScrollTo(200)
    }
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .verticalScroll(
                state
            )
    ) {
        repeat(10) {
            Text(text = "Item $it", modifier = Modifier.padding(5.dp))
        }
    }
}

//可滚动的修饰符
//scrollable与滚动修饰符不同，区别在于scrollable可检测滚动手势，但不会偏移其内容
@Composable
fun ScrollableSample() {
    var offset by remember {
        mutableStateOf(0f)
    }
    Box(
        Modifier
            .size(150.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState() { delta ->
                    offset += delta
                    delta
                }
            )
            .background(Color.LightGray), contentAlignment = Alignment.Center) {
        Text(text = offset.toString())
    }
}

//嵌套滚动
@Composable
fun NestedScrollSample() {
    val gradient = Brush.verticalGradient(
        0f to Color.Gray, 1000f to Color.White
    )
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)
                    ) //Text高度大于外部容器Box的高度
                }
            }
        }
    }
}

@Composable
fun DraggableSample() {
    var offset by remember {
        mutableStateOf(0f)
    }
    Text(text = "Drag me!", modifier = Modifier
        .offset { IntOffset(offset.roundToInt(), 0) }
        .draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta ->
                offset += delta
            }
        )
        .background(Color.LightGray))
}

//要控制整个拖动手势，通过pointerInput修饰符使用detectDragGestures
@Composable
fun DragWhereYouWantSample() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember {
            mutableStateOf(0f)
        }
        var offsetY by remember {
            mutableStateOf(0f)
        }
        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(
                    Color.LightGray
                )
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableSample() {
    val width = 96.dp
    val squareWidth = 48.dp

    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { squareWidth.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1)

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(modifier = Modifier
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
            .size(squareWidth)
            .background(Color.DarkGray))
    }
}

//多点触控：平移、缩放、旋转
@Composable
fun TransformableSample() {
    var scale by remember {
        mutableStateOf(1f)
    }
    var rotation by remember {
        mutableStateOf(0f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    val state = rememberTransformableState() { zoomChange, panChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += panChange
    }
    Box(
        Modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            .transformable(state)
            .background(Color.LightGray)
            .size(100.dp, 200.dp)
    )
}