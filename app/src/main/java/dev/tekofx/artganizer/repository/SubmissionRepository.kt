package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.ICharacterSubmissionCrossRef
import dev.tekofx.artganizer.dao.ISubmissionDao
import dev.tekofx.artganizer.entities.CharacterSubmissionCrossRef
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import kotlinx.coroutines.flow.Flow

class SubmissionRepository(
    private val submissionDao: ISubmissionDao,
    private val characterSubmissionCrossRef: ICharacterSubmissionCrossRef
) {

    // GET
    fun getAllSubmissions(): Flow<List<SubmissionWithArtist>> = submissionDao.getAllSubmissions()
    suspend fun getSubmissionWithArtist(submissionId: Long) =
        submissionDao.getSubmissionWithArtist(submissionId)

    // INSERT
    suspend fun insertSubmission(submission: Submission): Long = submissionDao.insert(submission)
    suspend fun insertCharacterSubmissionCrossRef(submission: CharacterSubmissionCrossRef) =
        characterSubmissionCrossRef.insert(submission)

    // UPDATE
    suspend fun updateSubmission(submission: Submission) =
        submissionDao.update(submission)


    // DELETE
    suspend fun deleteSubmission(submission: Submission) = submissionDao.delete(submission)
}
