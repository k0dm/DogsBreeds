package com.bugbender.dogsbreeds.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bugbender.dogsbreeds.presentation.navigation.AppNavHost
import com.bugbender.dogsbreeds.presentation.navigation.DogBreedsNavigationBar
import com.bugbender.dogsbreeds.presentation.ui.theme.DogsBreedsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogsBreedsTheme {
                val navController = rememberNavController()

                Scaffold(
                    content = { innerPaddings ->
                        AppNavHost(
                            navController = navController,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPaddings)
                        )
                    },
                    bottomBar = {
                        DogBreedsNavigationBar(navController)
                    },
                    modifier = Modifier.systemBarsPadding()
                )
            }
        }
    }
}


