package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.ISubmissionDao
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import kotlinx.coroutines.flow.Flow

class SubmissionRepository(private val submissionDao: ISubmissionDao) {
    fun getAllSubmissions(): Flow<List<SubmissionWithArtist>> = submissionDao.getAllSubmissions()

    fun getSubmission(id: Long): Flow<Submission?> = submissionDao.getSubmission(id)

    suspend fun getSubmissionWithArtist(submissionId: Long) =
        submissionDao.getSubmissionWithArtist(submissionId)

    suspend fun insertSubmission(submission: Submission): Long = submissionDao.insert(submission)

    suspend fun insertSubmissions(submissions: List<Submission>) =
        submissionDao.insertSubmissions(submissions)

    suspend fun deleteSubmission(submission: Submission) = submissionDao.delete(submission)

    suspend fun updateSubmission(submission: Submission) = submissionDao.update(submission)

    suspend fun updateSubmissionWithArtist(submission: Submission) =
        submissionDao.updateSubmissionWithArtist(submission)

}