package com.bugbender.dogsbreeds.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import com.bugbender.dogsbreeds.presentation.dogs.DogsScreen
import com.bugbender.dogsbreeds.ui.theme.DogsBreedsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogsBreedsTheme {
                DogsScreen(modifier = Modifier.systemBarsPadding())
            }
        }
    }
}