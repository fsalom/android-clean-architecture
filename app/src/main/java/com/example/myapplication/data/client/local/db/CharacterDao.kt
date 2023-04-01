package com.example.myapplication.data.source.local.db

import androidx.room.*
import com.example.myapplication.data.model.CharacterDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Characters")
    fun observeCharacters(): Flow<List<CharacterDbModel>>

    @Query("SELECT * FROM Characters")
    suspend fun getCharacters(): List<CharacterDbModel>

    @Query("SELECT * FROM Characters WHERE id = :id")
    suspend fun getCharacter(id: Int): CharacterDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterDbModel)

    @Delete
    suspend fun deleteCharacter(character: CharacterDbModel)

    @Query("DELETE FROM Characters")
    suspend fun deleteCharacters()
}