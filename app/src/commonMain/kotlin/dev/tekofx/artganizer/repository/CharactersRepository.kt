package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.ICharacterDao
import dev.tekofx.artganizer.entities.Character

class CharactersRepository(private val characterDao: ICharacterDao) {

    // INSERT
    suspend fun insertCharacter(character: Character) = characterDao.insert(character)

    // GET
    fun getAllCharactersWithSubmissions() = characterDao.getAllCharactersWithSubmissions()

    suspend fun getCharacterWithSubmissions(artistId: Long) =
        characterDao.getCharacterWithSubmissions(artistId)

    // UPDATE
    suspend fun updateCharacter(character: Character) = characterDao.update(character)

    // DELETE
    suspend fun deleteCharacter(character: Character) = characterDao.delete(character)


}