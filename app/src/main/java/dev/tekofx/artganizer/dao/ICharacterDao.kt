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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(character: Character)

    @Update
    suspend fun update(character: Character)

    @Delete
    suspend fun delete(character: Character)

    @Query("SELECT * from characters ORDER BY name ASC")
    fun getAllCharacters(): Flow<List<Character>>

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    suspend fun getCharacterWithSubmissions(characterId: Long): CharacterWithSubmissions?


}