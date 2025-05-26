package dev.tekofx.artganizer.ui.viewmodels.submissions

import dev.tekofx.artganizer.entities.SubmissionWithArtist

data class SubmissionUiState(
    val submissionDetails: SubmissionDetails = SubmissionDetails(),
    val isEntryValid: Boolean = false,
    val isSelected: Boolean = false,
)

data class SubmissionsUiState(
    val submissions: List<SubmissionUiState> = emptyList(),
    val selectedSubmissions: List<SubmissionUiState> = emptyList(),
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

fun List<SubmissionWithArtist>.toSubmissionsUiState(): SubmissionsUiState {
    return SubmissionsUiState(
        submissions = this.map { it.toSubmissionUiState() },
        selectedSubmissions = emptyList() // Default to empty selected list
    )
}

fun List<SubmissionWithArtist>.toSubmissionUiStateList(): List<SubmissionUiState> {
    return this.map { it.toSubmissionUiState() }
}