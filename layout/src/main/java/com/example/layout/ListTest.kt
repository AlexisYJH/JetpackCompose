package com.example.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

/**
 * @author AlexisYin
 * 列表
 */
//Column
@Composable
fun SimpleColumn() {
    Column {
        repeat(100) {
            Text(text = "Item $it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

//支持滚动
@Composable
fun SimpleList() {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text(text = "Item $it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

//LazyColumn
@Composable
fun LazyList() {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(100) {
            Text(text = "Item $it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

//滚动到顶部和底部
@Composable
fun ScrollingList() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column {
        Row {
           Button(modifier = Modifier.weight(1f), onClick = {
               coroutineScope.launch {
                   scrollState.animateScrollToItem(0)
               }
           }) {
               Text(text = "Scroll to the top")
           } 
            Button(modifier = Modifier.weight(1f), onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize-1)
                }
            }) {
                Text(text = "Scroll to the end")
            }
        }
        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }
}

//添加coil-compose依赖，网络权限
@Composable
fun ImageListItem(index : Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = rememberImagePainter(data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202004%2F23%2F20200423083019_zueev.thumb.400_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673281004&t=4535003c900d717b37953eae1ebf6ba7"),
            contentDescription = null, modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}