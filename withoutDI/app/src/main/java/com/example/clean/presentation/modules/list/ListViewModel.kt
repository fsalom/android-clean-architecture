package com.example.clean.presentation.modules.list
import com.example.clean.domain.usecase.CharacterUseCase

class ListViewModel {
    private val characterUseCase: CharacterUseCase

    constructor(usecase: CharacterUseCase) {
        this.characterUseCase = usecase
    }

    private fun getCharacters() {
        characterUseCase.getCharacters()
    }
}