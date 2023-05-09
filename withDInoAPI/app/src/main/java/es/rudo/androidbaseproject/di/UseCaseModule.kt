package es.rudo.androidbaseproject.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import es.rudo.androidbaseproject.domain.usecase.CharactersUseCase
import es.rudo.androidbaseproject.domain.usecase.impl.CharactersUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsCharactersUseCase(charactersUseCaseImpl: CharactersUseCaseImpl): CharactersUseCase
}