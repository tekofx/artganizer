package dev.tekofx.artganizer.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.tekofx.artganizer.entities.Submission
import kotlinx.coroutines.flow.Flow

interface ISubmissionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Submission)

    @Update
    suspend fun update(item: Submission)

    @Delete
    suspend fun delete(item: Submission)

    @Query("SELECT * from submissions WHERE id = :id")
    fun getSubmission(id: Int): Flow<Submission>

    @Query("SELECT * from submissions ORDER BY title ASC")
    fun getAllSubmissions(): Flow<List<Submission>>

}