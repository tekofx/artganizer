package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.submission.SubmissionsForm
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import kotlinx.coroutines.launch


@Composable
fun SubmissionCreationScreen(
    submissionsViewModel: SubmissionsViewModel, navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SubmissionsForm(
            submissionsViewModel.newSubmissionUiState,
            onItemValueChange = { newValue -> submissionsViewModel.updateNewUiState(newValue) },
            onSaveClick = {
                navHostController.popBackStack()
                scope.launch {
                    submissionsViewModel.saveSubmission(context)
                }
            },
        )

    }
}