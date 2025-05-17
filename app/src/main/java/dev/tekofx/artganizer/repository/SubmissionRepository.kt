package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.ISubmissionDao
import dev.tekofx.artganizer.entities.Submission
import kotlinx.coroutines.flow.Flow

class SubmissionRepository(private val submissionDao: ISubmissionDao) {
    fun getAllSubmissions(): Flow<List<Submission>> = submissionDao.getAllSubmissions()

    fun getSubmission(id: Int): Flow<Submission?> = submissionDao.getSubmission(id)

    suspend fun getSubmissionWithArtist(submissionId: Int) =
        submissionDao.getSubmissionWithArtist(submissionId)

    suspend fun insertSubmission(submission: Submission) = submissionDao.insert(submission)

    suspend fun deleteSubmission(submission: Submission) = submissionDao.delete(submission)

    suspend fun updateSubmission(submission: Submission) = submissionDao.update(submission)

    suspend fun updateSubmissionWithArtist(submission: Submission) =
        submissionDao.updateSubmissionWithArtist(submission)

}