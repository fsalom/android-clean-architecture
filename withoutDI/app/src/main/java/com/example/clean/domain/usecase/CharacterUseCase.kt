package com.example.clean.domain.usecase
import com.example.clean.data.model.Character
import com.example.clean.data.repository.CharacterRepository

interface CharacterUseCase {
    fun getCharacters(): List<Character>
}
class CharacterUseCaseImp: CharacterUseCase {
    private val repository: CharacterRepository

    constructor(repository: CharacterRepository) {
        this.repository = repository
    }
    override fun getCharacters(): List<Character> {
        return repository.getCharacters()
    }

}