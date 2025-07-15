package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.tekofx.artganizer.entities.CharacterSubmissionCrossRef

@Dao
interface ICharacterSubmissionCrossRef {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CharacterSubmissionCrossRef)

    // GET
    @Query("SELECT * from charactersubmissioncrossref WHERE submissionId = :id")
    suspend fun getCharacterSubmissionCrossRef(id: Long): List<CharacterSubmissionCrossRef>


    // DELETE
    @Delete
    suspend fun deleteCharacterSubmissionCrossRefs(submissionId: Long) {
        getCharacterSubmissionCrossRef(submissionId).forEach {
            delete(it)
        }
    }

    @Delete
    suspend fun delete(item: CharacterSubmissionCrossRef)

}