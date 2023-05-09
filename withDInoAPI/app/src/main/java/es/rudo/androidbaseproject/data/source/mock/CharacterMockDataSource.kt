package es.rudo.androidbaseproject.data.source.mock

import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.data.model.Location
import java.util.concurrent.Flow
import javax.inject.Inject

class CharacterMockDataSource @Inject constructor() {

    fun getCharacters(): List<Character> = listOf(
        Character(
            id = 1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            location = Location(
                name = "Earth"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        ),
        Character(
            id = 2,
            name = "Snuffles",
            status = "Alive",
            species = "Animal",
            image = "https://rickandmortyapi.com/api/character/avatar/329.jpeg",
        ),
        Character(
            id = 3,
            name = "Bearded Lady",
            status = "Dead",
            species = "Alien",
            location = Location(
                name = "Earth"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/32.jpeg",
        ),
    )

    fun getCharacter(id: Int): Character? = getCharacters().find { it.id == id }
}