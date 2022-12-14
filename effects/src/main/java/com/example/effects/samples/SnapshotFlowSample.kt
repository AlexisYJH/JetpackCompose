package com.example.effects.samples

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

/**
 * @author AlexisYin
 * snapshotFlow: 将State对象转换为Flow
 */
@Composable
fun SnapshotFlowSample() {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        items(100){ index ->
            Text(text = "Item $index")
        }
    }

    //当snapshotFlow块中的读取的State对象值发生变化时，
    // 如果新值与之前发出的值不相等， Flow会向其收集器发出新值
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .filter { it > 20 }
            .distinctUntilChanged()
            .collect {
                Log.d("TAG", "firstVisibleItemIndex: $it")
            }
    }

}