package es.rudo.androidbaseproject.di

import es.rudo.androidbaseproject.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import es.rudo.androidbaseproject.data.repository.CharacterRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsCharacterRepository(repositoryImpl: CharacterRepositoryImpl) : CharacterRepository
}