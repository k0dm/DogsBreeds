package com.bugbender.dogsbreeds.data.cloud

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {

    @GET("breeds/image/random")
    suspend fun fetchRandomDog(): DogDto

    @GET("breeds/list/all")
    suspend fun fetchAllBreeds(): Map<String, List<String>>

    @GET("breed/{breed}/{subBreed}/images/random")
    suspend fun fetchDogBySubBreed(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): DogDto

    @GET("breeds/{breed}/images/random")
    suspend fun fetchDogByBreed(
        @Path("breed") breed: String
    ): DogDto
}