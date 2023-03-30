package es.rudo.androidbaseproject.domain.usecase

import es.rudo.androidbaseproject.domain.repository.CharacterRepository
import javax.inject.Inject
import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.domain.helpers.resultOf

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(id: Int): Result<Character> {
        return resultOf {
            characterRepository.getCharacter(id)
        }
    }
}