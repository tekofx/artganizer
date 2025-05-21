package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.ICharacterSubmissionCrossRef
import dev.tekofx.artganizer.dao.ISubmissionDao
import dev.tekofx.artganizer.entities.CharacterSubmissionCrossRef
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.submissions.toSubmission
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
    private suspend fun insertSubmission(submission: Submission): Long =
        submissionDao.insert(submission)

    suspend fun insertSubmissionDetails(submissionDetails: SubmissionDetails): Long {
        val id = insertSubmission(submissionDetails.toSubmission())
        submissionDetails.characters.forEach { character ->
            insertCharacterSubmissionCrossRef(
                CharacterSubmissionCrossRef(
                    characterId = character.characterId,
                    submissionId = id
                )
            )
        }
        return id
    }

    suspend fun insertCharacterSubmissionCrossRef(submission: CharacterSubmissionCrossRef) =
        characterSubmissionCrossRef.insert(submission)

    // UPDATE
    private suspend fun updateSubmission(submission: Submission) =
        submissionDao.update(submission)

    suspend fun updateSubmissionWithArtist(submissionWithArtist: SubmissionWithArtist) {
        updateSubmission(submissionWithArtist.submission)
        characterSubmissionCrossRef.deleteCharacterSubmissionCrossRefs(submissionWithArtist.submission.submissionId)
        submissionWithArtist.characters.forEach { character ->
            insertCharacterSubmissionCrossRef(
                CharacterSubmissionCrossRef(
                    submissionId = submissionWithArtist.submission.submissionId,
                    characterId = character.characterId
                )
            )
        }
    }

    // DELETE
    suspend fun deleteSubmission(submission: Submission) = submissionDao.delete(submission)
}
