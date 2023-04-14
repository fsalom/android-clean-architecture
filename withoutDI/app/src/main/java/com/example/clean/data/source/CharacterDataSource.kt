package com.example.clean.data.source

interface CharacterDataSource {
    fun getCharacters(): List<Character>
}

class CharacterDataSourceImpl: CharacterDataSource {
    override fun getCharacters(): List<Character> {
        val characterList = ArrayList<Character>()
        return characterList
    }
}