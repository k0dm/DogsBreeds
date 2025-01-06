package com.bugbender.dogsbreeds.data

import com.bugbender.dogsbreeds.data.cloud.DogApiService
import com.bugbender.dogsbreeds.data.cloud.DogDto
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DogRepositoryTest {

    private lateinit var fakeApiService: FakeDogApiService
    private lateinit var repository: DogRepository

    @Before
    fun setUp() {
        fakeApiService = FakeDogApiService()
        repository = DogRepository.Base(fakeApiService)
    }

    @Test
    fun `fetchDog returns Success with breed and subBreed`() = runBlocking {
        fakeApiService.fakeResponse = DogDto("https://images.dog.ceo/breeds/pariah-indian/image.jpg")

        val result = repository.fetchDog()

        assertTrue(result is DogLoadResult.Success)
        val successResult = result as DogLoadResult.Success
        assertEquals("Pariah", successResult.dog.breed)
        assertEquals("Indian", successResult.dog.subBreed)
        assertEquals("https://images.dog.ceo/breeds/pariah-indian/image.jpg", successResult.dog.imageUrl)
    }

    @Test
    fun `fetchDog returns Success with breed and no subBreed`() = runBlocking {
        fakeApiService.fakeResponse = DogDto("https://images.dog.ceo/breeds/affenpinscher/image.jpg")

        val result = repository.fetchDog()

        assertTrue(result is DogLoadResult.Success)
        val successResult = result as DogLoadResult.Success
        assertEquals("Affenpinscher", successResult.dog.breed)
        assertNull(successResult.dog.subBreed)
        assertEquals("https://images.dog.ceo/breeds/affenpinscher/image.jpg", successResult.dog.imageUrl)
    }

    @Test
    fun `fetchDog returns Error on exception`() = runBlocking {
        fakeApiService.shouldThrowException = true

        val result = repository.fetchDog()

        assertTrue(result is DogLoadResult.Error)
        val errorResult = result as DogLoadResult.Error
        assertEquals("Fake network error", errorResult.message)
    }
}

class FakeDogApiService : DogApiService {
    var shouldThrowException = false
    var fakeResponse: DogDto? = null

    override suspend fun fetchRandomDog(): DogDto {
        if (shouldThrowException) {
            throw Exception("Fake network error")
        }
        return fakeResponse ?: throw Exception("No fake response set")
    }

    override suspend fun fetchAllBreeds(): Map<String, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDogBySubBreed(breed: String, subBreed: String): DogDto {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDogByBreed(breed: String): DogDto {
        TODO("Not yet implemented")
    }
}