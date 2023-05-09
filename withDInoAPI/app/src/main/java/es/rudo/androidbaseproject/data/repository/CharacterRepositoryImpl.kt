package es.rudo.androidbaseproject.data.repository

import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.data.source.mock.CharacterMockDataSource
import es.rudo.androidbaseproject.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val mockDataSource: CharacterMockDataSource
    private val remotoDataSource: CharacterMockDataSource
    private val mockDataSource: CharacterMockDataSource
    private val mockDataSource: CharacterMockDataSource

) : CharacterRepository {

    override suspend fun getCharacters(refresh: Boolean): List<Character> {
        return mockDataSource.getCharacters()
    }

    override suspend fun getCharacter(id: Int): Character? = mockDataSource.getCharacter(id)
}