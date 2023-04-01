package com.example.myapplication.data.source.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.myapplication.data.model.Character
import com.example.myapplication.data.model.toCharacter
import com.example.myapplication.di.IoDispatcher
import com.example.myapplication.data.helpers.apiHandler
import com.example.myapplication.data.source.remote.api.CharacterApi

class CharacterRemoteDataSource @Inject constructor(
    private val characterApi: CharacterApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getCharacters(page: Int = 1): List<Character> = withContext(dispatcher) {
        val response = apiHandler { characterApi.getCharacters(page) }

        response
            .results
            .map {
                it.toCharacter()
            }
    }
}