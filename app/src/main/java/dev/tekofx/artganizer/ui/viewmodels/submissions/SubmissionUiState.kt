package dev.tekofx.artganizer.ui.viewmodels.submissions

import dev.tekofx.artganizer.entities.SubmissionWithArtist

data class SubmissionUiState(
    val submissionDetails: SubmissionDetails = SubmissionDetails(),
    val isEntryValid: Boolean = false,
    val isSelected: Boolean = false,
)

fun SubmissionWithArtist.toSubmissionUiState(): SubmissionUiState {
    return SubmissionUiState(
        submissionDetails = SubmissionDetails(
            id = this.submission.submissionId,
            title = this.submission.title,
            description = this.submission.description,
            artistId = this.artist?.artistId ?: 0L,
            thumbnail = this.submission.thumbnail,
        ),
        isEntryValid = true, // Assuming the entry is valid for simplicity
        isSelected = false // Default to not selected
    )
}

fun List<SubmissionWithArtist>.toSubmissionUiStateList(): List<SubmissionUiState> {
    return this.map { it.toSubmissionUiState() }
}