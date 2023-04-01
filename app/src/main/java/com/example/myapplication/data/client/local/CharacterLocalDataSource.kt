package com.example.myapplication.data.source.local

import com.example.myapplication.data.model.Character
import com.example.myapplication.data.model.toCharacter
import com.example.myapplication.data.model.toDbModel
import com.example.myapplication.data.source.local.db.CharacterDao
import com.example.myapplication.data.source.local.preferences.AppPreferences
import com.example.myapplication.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(
    private val dao: CharacterDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    fun observeCharacters(): Flow<List<Character>> =
        dao
            .observeCharacters()
            .map { characters ->
                characters.map { it.toCharacter() }
            }

    suspend fun getCharacters(): List<Character> = withContext(dispatcher) {
        dao
            .getCharacters()
            .map { it.toCharacter() }
    }

    suspend fun getCharacter(id: Int): Character = withContext(dispatcher) {
        dao
            .getCharacter(id)
            .toCharacter()
    }

    suspend fun saveCharacters(character: List<Character>) = withContext(dispatcher) {
        dao.insertCharacters(character.map {
            it.toDbModel()
        })
    }

    suspend fun saveCharacter(character: Character) = withContext(dispatcher) {
        dao.insertCharacter(character.toDbModel())
    }

    suspend fun removeAllCharacters() = withContext(dispatcher) {
        dao.deleteCharacters()
    }
}