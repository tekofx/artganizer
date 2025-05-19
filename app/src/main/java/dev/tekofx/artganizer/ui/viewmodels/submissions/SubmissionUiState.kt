package dev.tekofx.artganizer.ui.viewmodels.submissions

import android.net.Uri

data class SubmissionUiState(
    val submissionDetails: SubmissionDetails = SubmissionDetails(),
    val imagePaths: List<Uri> = listOf<Uri>(),
    val isEntryValid: Boolean = false
)

