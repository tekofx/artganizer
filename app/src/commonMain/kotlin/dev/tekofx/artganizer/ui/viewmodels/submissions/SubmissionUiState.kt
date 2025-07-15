package dev.tekofx.artganizer.ui.viewmodels.submissions

import dev.tekofx.artganizer.entities.SubmissionWithArtist


data class SubmissionsUiState(
    val submissions: List<SubmissionWithArtist> = emptyList(),
    val selectedSubmissions: List<Long> = emptyList(),
)

fun List<SubmissionWithArtist>.toSubmissionsUiState(): SubmissionsUiState {
    return SubmissionsUiState(
        submissions = this,
        selectedSubmissions = emptyList()
    )
}