package com.bugbender.dogsbreeds.presentation.navigation

import kotlinx.serialization.Serializable

interface Route

@Serializable
object DogsRoute : Route

@Serializable
object BreedsRoute : Route