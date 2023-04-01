package com.example.myapplication.data.repository

import com.example.myapplication.domain.repository.CharacterRepository
import com.example.myapplication.data.source.local.CharacterLocalDataSource
import com.example.myapplication.data.source.remote.CharacterRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.example.myapplication.data.model.Character
import com.example.myapplication.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource
) : CharacterRepository {

    override fun observeCharacters(): Flow<List<Character>> {
        return localDataSource
            .observeCharacters()
            .onEach {
                if (it.isEmpty()) {
                    refreshCharacters()
                }
            }
    }

    override suspend fun getCharacters(refresh: Boolean): List<Character> {
        if (refresh) {
            refreshCharacters()
        }

        return localDataSource.getCharacters()
    }

    override suspend fun getCharacter(id: Int): Character = localDataSource.getCharacter(id)

    override suspend fun refreshCharacters(page: Int) {
        remoteDataSource
            .getCharacters(page)
            .also {
                saveCharacters(it)
            }
    }

    override suspend fun saveCharacters(characters: List<Character>) {
        localDataSource.saveCharacters(characters)
    }
}