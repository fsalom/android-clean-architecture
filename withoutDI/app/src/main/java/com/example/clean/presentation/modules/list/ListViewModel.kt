package com.example.clean.presentation.modules.list
import android.util.Log
import com.example.clean.data.model.Character
import com.example.clean.domain.usecase.CharacterUseCase

class ListViewModel {
    private val characterUseCase: CharacterUseCase

    constructor(usecase: CharacterUseCase) {
        this.characterUseCase = usecase
    }

    fun getCharacters(): List<Character> {
        return characterUseCase.getCharacters()
    }
}