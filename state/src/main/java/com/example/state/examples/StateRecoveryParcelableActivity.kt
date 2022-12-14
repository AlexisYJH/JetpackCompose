package com.example.state.examples

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.state.ui.theme.ComposeTheme
import kotlinx.android.parcel.Parcelize

/**
 * @author AlexisYin
 * 恢复状态Parcelize
 * 0. @Parcelize注解：添加插件kotlin-android-extensions
 *
 */
class StateRecoveryParcelableActivity : ComponentActivity() {

    @Parcelize
    data class City(val name: String, var country: String) : Parcelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                CityScreen()
            }
        }
    }

    @Composable
    private fun CityScreen() {
        //屏幕旋转后，显示初始值
        /*val (city, setCity) = remember {
            mutableStateOf(City("Madrid", "Spain"))
        }*/
        //临时数据保存，屏幕旋转后值不变
        val (city, setCity) = rememberSaveable {
            mutableStateOf(City("Madrid", "Spain"))
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
            TextButton(
                colors = ButtonDefaults.buttonColors(), onClick = { setCity(City("beijing", "China"))}) {
                Text(text = "Click to change")
            }
            Text(text = "${city.country} - ${city.name}")
        }
    }
}