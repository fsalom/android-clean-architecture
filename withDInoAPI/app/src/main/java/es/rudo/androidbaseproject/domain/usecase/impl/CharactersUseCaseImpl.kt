package es.rudo.androidbaseproject.domain.usecase.impl

import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.domain.repository.CharacterRepository
import es.rudo.androidbaseproject.domain.usecase.CharactersUseCase
import javax.inject.Inject

class CharactersUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : CharactersUseCase {

    override suspend fun getCharacters(): List<Character> {
        return characterRepository.getCharacters(false)
    }

    override suspend fun getCharacter(id: Int): Character? {
        return characterRepository.getCharacter(id)
    }
}