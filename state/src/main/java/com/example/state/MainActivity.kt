package com.example.state

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
import com.example.state.examples.*
import com.example.state.ui.theme.ComposeTheme
import com.example.state.todo.TodoActivity as StepZero
import com.example.state.todo.four.TodoActivity as StepFour
import com.example.state.todo.one.TodoActivity as StepOne
import com.example.state.todo.three.TodoActivity as StepThree
import com.example.state.todo.two.TodoActivity as StepTwo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                TodoStep(
                    listOf(
                        ActivityItem(
                            HelloComposeStateActivity::class.java,
                            R.string.hello_state
                        ),
                        ActivityItem(
                            HelloComposeStateActivityWithViewModel::class.java,
                            R.string.hello_state_with_viewmodel
                        ),
                        ActivityItem(
                            StepZero::class.java,
                            R.string.step
                        ),
                        ActivityItem(
                            StepOne::class.java,
                            R.string.step1
                        ),
                        ActivityItem(
                            StepTwo::class.java,
                            R.string.step2
                        ),
                        ActivityItem(
                            StepThree::class.java,
                            R.string.step3
                        ),
                        ActivityItem(
                            StepFour::class.java,
                            R.string.step4
                        ),
                        ActivityItem(
                            StateRecoveryParcelableActivity::class.java,
                            R.string.state_recovery_parcelable
                        ),
                        ActivityItem(
                            StateRecoveryMapSaverActivity::class.java,
                            R.string.state_recovery_mapsaver
                        ),
                        ActivityItem(
                            StateRecoveryListSaverActivity::class.java,
                            R.string.state_recovery_listsaver
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
    private fun TodoStep(items: List<ActivityItem>) {
        LazyColumn {
            itemsIndexed(items) { index, activityItem ->
                Button(
                    onClick = {
                        startActivity(Intent(this@MainActivity, activityItem.cls))
                    }, modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "STEP-${index} ${stringResource(id = activityItem.description)}")
                }
            }
        }
    }
}