package es.rudo.androidbaseproject.domain.usecase

import es.rudo.androidbaseproject.domain.repository.CharacterRepository
import javax.inject.Inject
import es.rudo.androidbaseproject.data.model.Character

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(): List<Character> {
        return characterRepository.getCharacters(false)
    }
}