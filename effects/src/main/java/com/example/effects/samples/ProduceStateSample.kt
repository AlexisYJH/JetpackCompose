package com.example.effects.samples

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * @author AlexisYin
 */
@Composable
fun ProduceStateSample() {
    val imageList = listOf<String>(
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202106%2F28%2F20210628011842_f20bd.thumb.1000_0.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671516464&t=a21c4e79b25bc8aea9e9bed7959a9d75",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202011%2F17%2F20201117165458_7aac7.thumb.1000_0.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671520484&t=831c0912e106a5314085bf8047a85488",
        "https://!!gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg3.doubanio.com%2Fview%2Fgroup_topic%2Fl%2Fpublic%2Fp445318120.jpg&refer=http%3A%2F%2Fimg3.doubanio.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671520372&t=fce82aab76d5065a0e4888398ccbaaec"
    )
    var index by remember {
        mutableStateOf(0)
    }
    val imageRepository = ImageRepository(LocalContext.current)
    val result = LoadNetworkImage(url = imageList[index], imageRepository = imageRepository)

    Column {
        Button(onClick = {
            index %= imageList.size
            if (++index == imageList.size) index = 0
        }) {
            Text(text = "第 ${index+1} 张图片，点击切换下一张")
        }
        //Image
        when(result.value) {
            is Result.Success -> {
                Image(bitmap = (result.value as Result.Success).image.imageBitmap, contentDescription = "image load success")
            }
            is Result.Error -> {
                Image(imageVector = Icons.Rounded.Warning, contentDescription = "image load success", modifier = Modifier.size(200.dp, 200.dp))
            }
            else -> {
                CircularProgressIndicator()//进度条
            }
        }
    }
}

@Composable
fun LoadNetworkImage(
    url: String,
    imageRepository: ImageRepository
): State<Result<Image>> {
    return produceState(initialValue = Result.Loading as Result<Image>, url, imageRepository) {
        Log.d("TAG", "Thread：${Thread.currentThread().name}")
        val image = imageRepository.load(url)
        value = if(image == null) {
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}