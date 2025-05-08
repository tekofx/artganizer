package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import dev.tekofx.artganizer.ui.components.SubmissionsForm
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModel
import kotlinx.coroutines.launch


@Composable
fun SubmissionCreationScreen(submissionsViewModel: SubmissionsViewModel) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SubmissionsForm(
            submissionsViewModel.submissionUiState,
            onItemValueChange = { submissionsViewModel::updateUiState },
            onSaveClick = { scope.launch { submissionsViewModel.saveSubmission() } },
        )

    }
}