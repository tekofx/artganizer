package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.gallery_outlined
import dev.tekofx.artganizer.ui.components.DialogLoader
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.submissions.form.SubmissionsForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SaveImagesOptions
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SubmissionCreationScreen(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    // ViewModels
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val charactersViewModel = koinViewModel<CharactersViewModel>()
    val tagsViewModel = koinViewModel<TagsViewModel>()
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()

    val saveImagesOption = submissionsViewModel.saveImagesOption
    val currentImageIndex by submissionsViewModel.currentImageIndex.collectAsState()
    val isLoading by submissionsViewModel.isLoading.collectAsState()
    val savingProgress by submissionsViewModel.savingProgress.collectAsState()
    val scope = rememberCoroutineScope()

    val files by submissionsViewModel.newFiles.collectAsState()

    DialogLoader(isLoading = isLoading, savingProgress)


    if (files.size > 1 && saveImagesOption == SaveImagesOptions.EMPTY) {
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
                drawableResource = Res.drawable.gallery_outlined
            )
            ButtonWithIcon(
                onClick = { submissionsViewModel.updateSaveImagesOption(SaveImagesOptions.MULTIPLE_SUBMISSIONS) },
                text = "Multiple Submissions",
                drawableResource = Res.drawable.gallery_outlined
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SubmissionsForm(
                uris = files.map { it.path },
                artistsViewModel = artistsViewModel,
                charactersViewModel = charactersViewModel,
                tagsViewModel = tagsViewModel,
                submissionDetails = submissionsViewModel.newSubmissionDetails,
                onItemValueChange = { newValue -> submissionsViewModel.updateNewUiState(newValue) },
                onSaveClick = {
                    scope.launch {
                        submissionsViewModel.saveSubmission()
                        onSaveClick()
                    }
                },
                onCancelClick = {
                    submissionsViewModel.clearNewUiState()
                    onCancelClick()
                },
                currentImageIndex = currentImageIndex
            )
        }
    }
}