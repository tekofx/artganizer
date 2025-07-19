package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.ICharacterSubmissionCrossRef
import dev.tekofx.artganizer.dao.IImageDao
import dev.tekofx.artganizer.dao.ISubmissionDao
import dev.tekofx.artganizer.dao.ITagSubmissionCrossRef
import dev.tekofx.artganizer.entities.CharacterSubmissionCrossRef
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.entities.TagSubmissionCrossRef
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.submissions.toSubmission
import dev.tekofx.artganizer.utils.ImageManager
import kotlinx.coroutines.flow.Flow


interface SubmissionRepositoryInterface {
    fun getAllSubmissions(): Flow<List<SubmissionWithArtist>>
    suspend fun getSubmissionWithArtist(submissionId: Long): SubmissionWithArtist?
    suspend fun insertSubmissionDetails(submissionDetails: SubmissionDetails): Long
    suspend fun updateSubmissionWithArtist(submissionWithArtist: SubmissionWithArtist)
    suspend fun deleteSubmission(imageManager: ImageManager, submission: SubmissionWithArtist)
    suspend fun deleteSubmissions(
        imageManager: ImageManager,
        submissions: List<SubmissionWithArtist>
    )
}

class SubmissionRepository(
    private val submissionDao: ISubmissionDao,
    private val imageDao: IImageDao,
    private val characterSubmissionCrossRef: ICharacterSubmissionCrossRef,
    private val tagSubmissionCrossRef: ITagSubmissionCrossRef
) : SubmissionRepositoryInterface {

    // GET
    override fun getAllSubmissions(): Flow<List<SubmissionWithArtist>> =
        submissionDao.getAllSubmissions()

    override suspend fun getSubmissionWithArtist(submissionId: Long) =
        submissionDao.getSubmissionWithArtist(submissionId)


    // INSERT
    private suspend fun insertSubmission(submission: Submission): Long =
        submissionDao.insert(submission)

    override suspend fun insertSubmissionDetails(submissionDetails: SubmissionDetails): Long {

        val id = insertSubmission(submissionDetails.toSubmission())
        submissionDetails.characters.forEach { character ->
            insertCharacterSubmissionCrossRef(
                CharacterSubmissionCrossRef(
                    characterId = character.characterId,
                    submissionId = id
                )
            )
        }

        submissionDetails.tags.forEach { tag ->
            insertTagSubmissionCrossRef(
                TagSubmissionCrossRef(
                    tagId = tag.tagId,
                    submissionId = id
                )
            )
        }

        return id
    }

    suspend fun insertCharacterSubmissionCrossRef(submission: CharacterSubmissionCrossRef) =
        characterSubmissionCrossRef.insert(submission)

    suspend fun insertTagSubmissionCrossRef(submission: TagSubmissionCrossRef) =
        tagSubmissionCrossRef.insert(submission)

    // UPDATE
    private suspend fun updateSubmission(submission: Submission) =
        submissionDao.update(submission)

    override suspend fun updateSubmissionWithArtist(submissionWithArtist: SubmissionWithArtist) {
        updateSubmission(submissionWithArtist.submission)
        characterSubmissionCrossRef.deleteCharacterSubmissionCrossRefs(submissionWithArtist.submission.submissionId)

        // Insert new character submission cross references
        submissionWithArtist.characters.forEach { character ->
            insertCharacterSubmissionCrossRef(
                CharacterSubmissionCrossRef(
                    submissionId = submissionWithArtist.submission.submissionId,
                    characterId = character.characterId
                )
            )
        }

        // Insert new tags submission cross references
        submissionWithArtist.tags.forEach { tag ->
            insertTagSubmissionCrossRef(
                TagSubmissionCrossRef(
                    tagId = tag.tagId,
                    submissionId = submissionWithArtist.submission.submissionId
                )
            )
        }
    }

    // DELETE
    override suspend fun deleteSubmission(
        imageManager: ImageManager,
        submission: SubmissionWithArtist
    ) {
        // Delete submission
        submissionDao.delete(submission.submission)

        // Delete character submission cross references
        submission.submission.characterId?.let {
            characterSubmissionCrossRef.deleteCharacterSubmissionCrossRefs(
                it
            )
        }

        // Delete images associated with the submission
        submission.images.forEach {
            imageDao.delete(it)
            imageManager.removeImageFromInternalStorage(it.uri)
        }

        // Delete thumbnail
        imageManager.removeImageFromInternalStorage(submission.submission.thumbnail)

    }

    override suspend fun deleteSubmissions(
        imageManager: ImageManager,
        submissions: List<SubmissionWithArtist>
    ) {
        submissions.forEach { submission ->
            deleteSubmission(imageManager, submission)
        }
    }
}
