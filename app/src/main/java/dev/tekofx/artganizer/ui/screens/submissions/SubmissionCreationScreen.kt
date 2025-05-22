package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.submission.SubmissionsForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SaveImagesOptions
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import kotlinx.coroutines.launch

@Composable
fun SubmissionCreationScreen(
    navHostController: NavHostController,
    submissionsViewModel: SubmissionsViewModel,
    artistsViewModel: ArtistsViewModel,
    charactersViewModel: CharactersViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val saveImagesOption = submissionsViewModel.saveImagesOption
    val currentImageIndex by submissionsViewModel.currentImageIndex.collectAsState()
    val isLoading by submissionsViewModel.isLoading.collectAsState()

    DialogLoader(isLoading = isLoading)

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
            Text(
                "Select how you want to import images",
                style = MaterialTheme.typography.titleLarge
            )
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
                uris = submissionsViewModel.uris,
                artistsViewModel = artistsViewModel,
                charactersViewModel = charactersViewModel,
                submissionDetails = submissionsViewModel.newSubmissionDetails,
                onItemValueChange = { newValue -> submissionsViewModel.updateNewUiState(newValue) },
                onSaveClick = {
                    scope.launch {
                        submissionsViewModel.saveSubmission(context)
                        navHostController.popBackStack()
                    }
                },
                onCancelClick = {
                    submissionsViewModel.clearNewUiState()
                    navHostController.popBackStack()
                },
                currentImageIndex = currentImageIndex
            )
        }
    }
}

@Composable
fun DialogLoader(
    isLoading: Boolean,
) {
    if (isLoading) {
        Dialog(
            onDismissRequest = {},
        ) {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Saving Submission")
                    CircularProgressIndicator()
                }
            }
        }
    }
}
