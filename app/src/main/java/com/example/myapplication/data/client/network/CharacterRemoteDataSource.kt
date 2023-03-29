package es.rudo.androidbaseproject.data.source.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.data.model.toCharacter
import es.rudo.androidbaseproject.di.IoDispatcher
import es.rudo.androidbaseproject.data.helpers.apiHandler
import es.rudo.androidbaseproject.data.source.remote.api.CharacterApi

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