package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.Character
import com.example.myapplication.domain.repository.CharacterRepository
import javax.inject.Inject
class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(): List<Character> {
        return characterRepository.getCharacters(false)
    }
}