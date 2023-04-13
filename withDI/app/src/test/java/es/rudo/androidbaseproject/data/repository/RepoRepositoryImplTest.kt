package es.rudo.androidbaseproject.data.repository

import es.rudo.androidbaseproject.CoroutineRule
import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.data.source.local.CharacterLocalDataSource
import es.rudo.androidbaseproject.data.source.remote.CharacterRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource

@ExperimentalCoroutinesApi
class RepoRepositoryImplTest {

    private val character1 = Character(id = 1, name = "Character1", status = "Status1")
    private val character2 = Character(id = 2, name = "Character2", status = "Status2")
    private val character3 = Character(id = 3, name = "Character3", status = "Status3")
    private val newCharacter = Character(id = 4, name = "NewCharacter", status = "NewStatus")

    private val remoteCharacters = listOf(character1, character2)
    private val localCharacters = listOf(character3)
    private val newCharacters = listOf(newCharacter)

    @RelaxedMockK
    private lateinit var remoteDataSource: CharacterRemoteDataSource
    @RelaxedMockK
    private lateinit var localDataSource: CharacterLocalDataSource

    lateinit var characterRepository: CharacterRepositoryImpl

    @get:Rule
    val mainCoroutineRule = CoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        createRepository()
    }

    private fun createRepository() {
        characterRepository = CharacterRepositoryImpl(
            remoteDataSource, localDataSource
        )
    }

    @Test
    fun `When observe characters flow then check if it's the expected item`() = runTest {
        coEvery { localDataSource.observeCharacters() } returns  flow { emit(localCharacters.toList()) }

        val firstItem = characterRepository.observeCharacters().first()

        assertEquals(localCharacters, firstItem)
    }

    @Test
    fun `When sources are empty then getCharacters() doesn't fail and return empty`() = runTest {
        coEvery { localDataSource.getCharacters() } returns emptyList()
        coEvery { remoteDataSource.getCharacters() } returns emptyList()

        assertTrue(characterRepository.getCharacters() == emptyList<Character>())
    }

    @Test
    fun `When api call then repository caches correctly`() = runTest {
        coEvery { remoteDataSource.getCharacters() } returns newCharacters.toMutableList()

        val initial = characterRepository.getCharacters(true)
        val second = characterRepository.getCharacters(false)

        assertEquals(initial, second)
    }

    @Test
    fun `When a single character is requested can find it with id`() = runTest {
        coEvery { localDataSource.getCharacter(localCharacters.first().id) } returns localCharacters.first()

        val singleCharacter = localCharacters.first()

        val resultCharacter = characterRepository.getCharacter(singleCharacter.id)

        assertEquals(singleCharacter, resultCharacter)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    @NullSource
    fun isEvenTest(actualNumber: Int?) {
        if (actualNumber != null) {
            val expectedResult = actualNumber % 2 == 0
            assertEquals(expectedResult, actualNumber.isEven())
        }
    }
}

fun Int?.isEven(): Boolean {
    return if(this != null) {
        this % 2 == 0
    } else throw RuntimeException("")
}

interface Adios {
    fun hasta(): Boolean
}