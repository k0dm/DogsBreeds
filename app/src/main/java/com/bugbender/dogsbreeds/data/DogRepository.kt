package com.bugbender.dogsbreeds.data

import com.bugbender.dogsbreeds.data.cloud.DogApiService

import javax.inject.Inject

interface DogRepository {

    suspend fun fetchDog(): DogLoadResult

    class Base @Inject constructor(private val apiService: DogApiService) : DogRepository {

        override suspend fun fetchDog(): DogLoadResult {
            return try {
                val dogDto = apiService.fetchRandomDog()
                val parts = dogDto.message
                    .removePrefix("https://images.dog.ceo/breeds/")
                    .split("/")
                    .get(0)
                    .split("-")

                DogLoadResult.Success(
                    dog = Dog(
                        imageUrl = dogDto.message,
                        breed = parts[0].replaceFirstChar { it.uppercase() },
                        subBreed = if (parts.size > 1) parts[1].replaceFirstChar { it.uppercase() } else null
                    )
                )
            } catch (e: Exception) {
                DogLoadResult.Error(message = e.message!!)
            }
        }
    }
}

interface DogLoadResult {
    data class Success(val dog: Dog) : DogLoadResult
    data class Error(val message: String) : DogLoadResult
}

data class Dog(
    val imageUrl: String,
    val breed: String,
    val subBreed: String? = null
)