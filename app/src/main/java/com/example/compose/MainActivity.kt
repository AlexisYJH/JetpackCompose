package com.example.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                ActivityList(
                    items = listOf(
                        ActivityItem(
                            MainActivity01::class.java,
                            R.string.main01
                        ),
                        ActivityItem(
                            MainActivity02::class.java,
                            R.string.main02
                        ),
                        ActivityItem(
                            MainActivity03::class.java,
                            R.string.main03
                        ),
                        ActivityItem(
                            MainActivity04::class.java,
                            R.string.main04
                        ),
                        ActivityItem(
                            MainActivity05::class.java,
                            R.string.main05
                        ),
                        ActivityItem(
                            MainActivity06::class.java,
                            R.string.main06
                        )
                    )
                )
            }
        }
    }

    data class ActivityItem(
        val cls: Class<out ComponentActivity>,
        @StringRes val description: Int
    )

    @Composable
    private fun ActivityList(items: List<ActivityItem>) {
        LazyColumn {
            itemsIndexed(items) { index, activityItem ->
                Button(
                    onClick = {
                        startActivity(Intent(this@MainActivity, activityItem.cls))
                    }, modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "${stringResource(id = activityItem.description)}")
                }
            }
        }
    }
}



