package com.example.state.examples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.state.ui.theme.ComposeTheme

/**
 * @author AlexisYin
 * 恢复状态ListSaver
 */
class StateRecoveryListSaverActivity : ComponentActivity() {

    data class City(val name: String, var country: String)

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
        val citySaver = listSaver<City, Any>(
            save = { listOf(it.name, it.country) },
            restore = {City(it[0] as String, it[1] as String)}
        )

        val (city, setCity) = rememberSaveable(stateSaver = citySaver) {
            mutableStateOf(City("Madrid", "Spain"))
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
            TextButton(
                colors = ButtonDefaults.buttonColors(),
                onClick = { setCity(City("beijing", "China")) }) {
                Text(text = "Click to change")
            }
            Text(text = "${city.country} - ${city.name}")
        }
    }
}