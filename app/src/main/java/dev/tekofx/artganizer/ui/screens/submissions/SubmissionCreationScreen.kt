package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.submission.SubmissionsForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SaveImagesOptions
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import kotlinx.coroutines.launch


@Composable
fun SubmissionCreationScreen(
    submissionsViewModel: SubmissionsViewModel,
    artistsViewModel: ArtistsViewModel,
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val saveImagesOption = submissionsViewModel.saveImagesOption

    if (submissionsViewModel.uris.size > 1 && saveImagesOption == SaveImagesOptions.EMPTY) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Select how you can to import images", style = MaterialTheme.typography.titleLarge)
            ButtonWithIcon(
                onClick = { submissionsViewModel.updateSaveImagesOption(SaveImagesOptions.SINGLE_SUBMISSION) },
                text = "Single Submission",
                iconResource = IconResource.fromDrawableResource(R.drawable.square_plus)
            )
            ButtonWithIcon(
                onClick = { submissionsViewModel.updateSaveImagesOption(SaveImagesOptions.MULTIPLE_SUBMISSIONS) },
                text = "Multiple Submissions",
                iconResource = IconResource.fromDrawableResource(R.drawable.copy_plus)
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SubmissionsForm(
                submissionsViewModel.uris,
                artistsViewModel,
                submissionsViewModel.newSubmissionDetails,
                onItemValueChange = { newValue -> submissionsViewModel.updateNewUiState(newValue) },
                onSaveClick = {
                    navHostController.popBackStack()
                    scope.launch {
                        submissionsViewModel.saveSubmission(context)
                    }
                },
                onCancelClick = {
                    navHostController.popBackStack()
                }
            )

        }
    }
}