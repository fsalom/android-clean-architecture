package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.helpers.resultOf
import com.example.myapplication.domain.repository.CharacterRepository
import javax.inject.Inject

class RefreshCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(page: Int = 1): Result<Unit> = resultOf {
        characterRepository.refreshCharacters(page)
    }
}