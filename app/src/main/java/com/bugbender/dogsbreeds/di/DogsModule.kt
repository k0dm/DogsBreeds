package com.bugbender.dogsbreeds.di

import com.bugbender.dogsbreeds.data.DogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DogsModule {

    @Binds
    @ViewModelScoped
    abstract fun dogRepository(repository: DogRepository.Base): DogRepository
}