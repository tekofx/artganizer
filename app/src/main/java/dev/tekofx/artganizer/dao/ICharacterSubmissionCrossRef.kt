package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.tekofx.artganizer.entities.CharacterSubmissionCrossRef

@Dao
interface ICharacterSubmissionCrossRef {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CharacterSubmissionCrossRef)

}