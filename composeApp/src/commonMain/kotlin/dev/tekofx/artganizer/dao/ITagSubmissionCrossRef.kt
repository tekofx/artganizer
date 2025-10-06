package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.tekofx.artganizer.entities.TagSubmissionCrossRef

@Dao
interface ITagSubmissionCrossRef {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TagSubmissionCrossRef)

    // GET
    @Query("SELECT * from tagsubmissioncrossref WHERE submissionId = :id")
    suspend fun getTagSubmissionCrossRef(id: Long): List<TagSubmissionCrossRef>


    // DELETE
    @Delete
    suspend fun deleteTagSubmissionCrossRefs(submissionId: Long) {
        getTagSubmissionCrossRef(submissionId).forEach {
            delete(it)
        }
    }

    @Delete
    suspend fun delete(item: TagSubmissionCrossRef)

}