package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun observeCharacters(): Flow<List<Character>>

    suspend fun getCharacters(refresh: Boolean = true): List<Character>

    suspend fun getCharacter(id: Int): Character

    suspend fun refreshCharacters(page: Int = 1)

    suspend fun saveCharacters(characters: List<Character>)
}