package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import kotlinx.coroutines.flow.Flow

@Dao
interface ICharacterDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(character: Character)

    // GET
    @Query("SELECT * from characters ORDER BY name ASC")
    fun getAllCharacters(): Flow<List<Character>>

    @Transaction
    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharactersWithSubmissions(): Flow<List<CharacterWithSubmissions>>

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    suspend fun getCharacterWithSubmissions(characterId: Long): CharacterWithSubmissions?

    // UPDATE
    @Update
    suspend fun update(character: Character)

    // DELETE
    @Delete
    suspend fun delete(character: Character)


}