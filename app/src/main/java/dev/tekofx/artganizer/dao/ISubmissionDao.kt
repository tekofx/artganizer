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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Submission): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubmissions(items: List<Submission>)

    @Update
    suspend fun update(item: Submission)

    @Delete
    suspend fun delete(item: Submission)

    @Query("SELECT * from submissions WHERE id = :id")
    fun getSubmission(id: Int): Flow<Submission>

    @Transaction
    @Query("SELECT * FROM submissions WHERE id = :submissionId")
    suspend fun getSubmissionWithArtist(submissionId: Int): SubmissionWithArtist?

    @Transaction
    suspend fun updateSubmissionWithArtist(submission: Submission) {
        update(submission)
        getSubmissionWithArtist(submission.id)
    }

    @Query("SELECT * from submissions ORDER BY title ASC")
    fun getAllSubmissions(): Flow<List<SubmissionWithArtist>>

}