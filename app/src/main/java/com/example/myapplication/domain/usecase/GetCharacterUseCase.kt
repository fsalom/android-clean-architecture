package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.CharacterRepository
import javax.inject.Inject
import com.example.myapplication.data.model.Character
import com.example.myapplication.domain.helpers.resultOf

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(id: Int): Result<Character> {
        return resultOf {
            characterRepository.getCharacter(id)
        }
    }
}