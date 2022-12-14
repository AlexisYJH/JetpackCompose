package com.example.effects

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.effects.samples.*
import com.example.effects.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                //LaunchedEffectSample()
                //ScaffoldSample()
                //RememberUpdatedStateSample()
                //DisposedEffectSample(onBackPressedDispatcher)
                //ProduceStateSample()
                //DerivedStateOfSample()
                SnapshotFlowSample()
            }
        }
    }
}