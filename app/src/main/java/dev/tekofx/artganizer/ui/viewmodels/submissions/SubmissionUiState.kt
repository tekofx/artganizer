package dev.tekofx.artganizer.ui.viewmodels.submissions

import android.net.Uri
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.utils.stringToDate
import java.util.Date

data class SubmissionUiState(
    val submissionDetails: SubmissionDetails = SubmissionDetails(),
    val imagePaths: List<Uri> = listOf<Uri>(),
    val isEntryValid: Boolean = false
)

fun SubmissionUiState.toSubmissionWithArtist(): SubmissionWithArtist = SubmissionWithArtist(
    Submission(
        id = submissionDetails.id,
        title = submissionDetails.title,
        description = submissionDetails.description,
        imagesPath = submissionDetails.imagesPath,
        rating = submissionDetails.rating,
        date = stringToDate(submissionDetails.date) ?: Date(),
        size = submissionDetails.size,
        dimensions = submissionDetails.dimensions,
        extension = submissionDetails.extension,
        palette = submissionDetails.palette,
        artistId = submissionDetails.artistId
    ),
    artist = submissionDetails.artist
)