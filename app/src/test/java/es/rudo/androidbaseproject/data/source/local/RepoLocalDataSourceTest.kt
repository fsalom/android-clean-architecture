package es.rudo.androidbaseproject.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import es.rudo.androidbaseproject.CoroutineRule
import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.data.source.local.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class RepoLocalDataSourceTest {

    private lateinit var localDataSource: CharacterLocalDataSource
    private lateinit var database: AppDatabase

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        localDataSource = CharacterLocalDataSource(database.characterDao(), Dispatchers.Main)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `When storing a character to db, then retrieving returns the same character`() = runTest {
        val newCharacter = Character(id = 1, name = "Character1", status = "Status1")
        localDataSource.saveCharacter(newCharacter)

        val result = localDataSource.getCharacter(newCharacter.id)

        assertEquals(result.id, 1)
        assertEquals(result.name, "Character1")
        assertEquals(result.status, "Status1")
    }

    @Test
    fun `When storing 2 characters, getRepos() return exactly 2`() = runTest {
        val character1 = Character(id = 1, name = "Character1", status = "Status1")
        val character2 = Character(id = 2, name = "Character2", status = "Status2")

        localDataSource.saveCharacter(character1)
        localDataSource.saveCharacter(character2)

        val result = localDataSource.getCharacters()
        assertEquals(result.size, 2)
    }

    @Test
    fun `When removeAllRepos() is called, then the result is an empty list`() = runTest {
        val character = Character(1)

        localDataSource.saveCharacter(character)

        localDataSource.removeAllCharacters()

        val result = localDataSource.getCharacters()
        assertTrue(result.isEmpty())
    }
}