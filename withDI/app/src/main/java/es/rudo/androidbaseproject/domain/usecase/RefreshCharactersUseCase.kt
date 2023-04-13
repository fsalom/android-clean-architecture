package es.rudo.androidbaseproject.domain.usecase

import es.rudo.androidbaseproject.domain.helpers.resultOf
import es.rudo.androidbaseproject.domain.repository.CharacterRepository
import javax.inject.Inject

class RefreshCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(page: Int = 1): Result<Unit> = resultOf {
        characterRepository.refreshCharacters(page)
    }
}