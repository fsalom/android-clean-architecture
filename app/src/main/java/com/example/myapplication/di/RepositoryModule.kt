package com.example.myapplication.di

import com.example.myapplication.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.example.myapplication.data.repository.CharacterRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsCharacterRepository(repositoryImpl: CharacterRepositoryImpl) : CharacterRepository
}