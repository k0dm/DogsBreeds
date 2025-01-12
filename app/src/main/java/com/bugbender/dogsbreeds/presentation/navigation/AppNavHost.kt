package com.bugbender.dogsbreeds.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bugbender.dogsbreeds.presentation.ui.dogs.DogsScreen
import com.bugbender.dogsbreeds.presentation.ui.dogs.DogsViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DogsRoute,
        modifier = modifier
    ) {
        composable<DogsRoute> {
            val viewModel: DogsViewModel = hiltViewModel()
            DogsScreen(viewModel = viewModel)
        }
        composable<BreedsRoute> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Dog Breeds Screen")
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: Route) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }