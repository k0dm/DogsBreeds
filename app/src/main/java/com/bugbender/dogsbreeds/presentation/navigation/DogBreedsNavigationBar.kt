package com.bugbender.dogsbreeds.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bugbender.dogsbreeds.R
import com.bugbender.dogsbreeds.presentation.ui.theme.DogsBreedsTheme

@Composable
fun DogBreedsNavigationBar(navController: NavHostController) {
    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination

        NavigationBarItem(
            selected = currentDestination?.hasRoute<DogsRoute>() ?: false,
            onClick = {
                navController.navigateSingleTopTo(DogsRoute)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.dog),
                    contentDescription = stringResource(R.string.dog_navigation_icon)
                )
            },
            label = {
                Text(text = stringResource(R.string.dogs))
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute<BreedsRoute>() ?: false,
            onClick = {
                navController.navigateSingleTopTo(BreedsRoute)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.bones),
                    contentDescription = stringResource(R.string.breeds_navigation_icon)
                )
            },
            label = {
                Text(text = stringResource(R.string.breeds))
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Preview
@Composable
private fun DogBreedsNavigationBarPreview() {
    DogsBreedsTheme {
        DogBreedsNavigationBar(NavHostController(LocalContext.current))
    }
}