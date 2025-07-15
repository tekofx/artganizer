package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import kotlinx.coroutines.flow.Flow

@Dao
interface ISubmissionDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Submission): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubmissions(items: List<Submission>)

    // GET
    @Query("SELECT * from submissions WHERE submissionId = :id")
    fun getSubmission(id: Long): Flow<Submission>

    @Transaction
    @Query("SELECT * FROM submissions WHERE submissionId = :submissionId")
    suspend fun getSubmissionWithArtist(submissionId: Long): SubmissionWithArtist?

    @Transaction
    @Query("SELECT * from submissions ORDER BY title ASC")
    fun getAllSubmissions(): Flow<List<SubmissionWithArtist>>

    // UPDATE
    @Update
    suspend fun update(item: Submission)

    // DELETE
    @Delete
    suspend fun delete(item: Submission)
}