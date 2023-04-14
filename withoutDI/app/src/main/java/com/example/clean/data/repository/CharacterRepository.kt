package com.example.clean.data.repository

import com.example.clean.data.source.CharacterDataSource
import com.example.clean.data.source.CharacterDataSourceImpl

interface CharacterRepository {
    fun getCharacters(): List<Character>
}
class CharacterRepositoryImpl: CharacterRepository {
    private val remoteDataSource: CharacterDataSource
    constructor(dataSource: CharacterDataSource) {
        this.remoteDataSource = dataSource
    }
    override fun getCharacters(): List<Character> {
        val characterList = remoteDataSource.getCharacters()
        return characterList
    }
}