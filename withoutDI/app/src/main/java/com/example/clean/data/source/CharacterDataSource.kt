package com.example.clean.data.source
import com.example.clean.data.model.Character
interface CharacterDataSource {
    fun getCharacters(): List<Character>
}

class CharacterDataSourceImpl: CharacterDataSource {
    override fun getCharacters(): List<Character> {
        return createListOfCharacters()
    }

    private fun createListOfCharacters(): List<Character> {
        val characterList = ArrayList<Character>()
        characterList.add(
            Character(id = 0,
            name = "Rick",
            status = "alive",
            species = "human")
        )
        characterList.add(Character(id = 0,
            name = "Morty",
            status = "alive",
            species = "human"))
        characterList.add(Character(id = 0,
            name = "Rick",
            status = "alive",
            species = "human"))
        return characterList
    }
}