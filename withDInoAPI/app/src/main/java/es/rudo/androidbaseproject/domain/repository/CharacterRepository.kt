package es.rudo.androidbaseproject.domain.repository

import es.rudo.androidbaseproject.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacters(refresh: Boolean = true): List<Character>

    suspend fun getCharacter(id: Int): Character?
}