package es.rudo.androidbaseproject.domain.usecase

import es.rudo.androidbaseproject.data.model.Character

interface CharactersUseCase {

    suspend fun getCharacters(): List<Character>

    suspend fun getCharacter(id: Int): Character?
}