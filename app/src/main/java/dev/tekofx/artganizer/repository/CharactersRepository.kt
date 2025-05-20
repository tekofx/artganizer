package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.ICharacterDao
import dev.tekofx.artganizer.entities.Character

class CharactersRepository(private val characterDao: ICharacterDao) {

    suspend fun insertCharacter(character: Character) = characterDao.insert(character)

    suspend fun deleteCharacter(character: Character) = characterDao.delete(character)

    suspend fun updateCharacter(character: Character) = characterDao.update(character)

    fun getAllCharacters() = characterDao.getAllCharacters()

    suspend fun getCharacterWithSubmissions(artistId: Long) =
        characterDao.getCharacterWithSubmissions(artistId)

}