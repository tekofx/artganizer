package dev.tekofx.artganizer.ui.components.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tekofx.artganizer.managers.DialogContent
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.components.forms.DesktopSubmissionForm
import dev.tekofx.artganizer.ui.components.input.form.CharacterForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import dev.tekofx.artganizer.viewmodel.DesktopUiViewModel
import io.github.vinceglb.filekit.path

@Composable
fun CreateDialog(
    artistsViewModel: ArtistsViewModel = viewModel(),
    desktopUiViewModel: DesktopUiViewModel = viewModel(),
    submissionsViewModel: SubmissionsViewModel = viewModel(),
    charactersViewModel: CharactersViewModel = viewModel(),
    tagsViewModel: TagsViewModel = viewModel()
) {
    val dialogContent by desktopUiViewModel.dialogContent.collectAsState()
    val files by submissionsViewModel.newFiles.collectAsState()
    val currentImageIndex by submissionsViewModel.currentImageIndex.collectAsState()
    if (dialogContent != DialogContent.NONE) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                usePlatformDefaultWidth = false, // Prevents default width constraints
            )
        ) {
            Surface(
                modifier = Modifier.padding(50.dp).fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceContainerLow
            ) {
                when (dialogContent) {
                    DialogContent.NONE -> null
                    DialogContent.SUBMISSIONS_FORM -> DesktopSubmissionForm(
                        uris = files.map { it.path },
                        artistsViewModel = artistsViewModel,
                        submissionDetails = submissionsViewModel.newSubmissionDetails,
                        charactersViewModel = charactersViewModel,
                        tagsViewModel = tagsViewModel,
                        currentImageIndex = currentImageIndex,
                        onItemValueChange = { newValue ->
                            submissionsViewModel.updateNewUiState(
                                newValue
                            )
                        },
                        onSaveClick = {
                            submissionsViewModel.saveSubmission()
                            desktopUiViewModel.setDialogContent(DialogContent.NONE)
                        },
                        onCancelClick = {
                            submissionsViewModel.clearNewUiState()
                            submissionsViewModel.clearNewFiles()
                            desktopUiViewModel.setDialogContent(DialogContent.NONE)
                        }
                    )

                    DialogContent.ARTIST_FORM -> ArtistForm(
                        artistUiState = artistsViewModel.newArtistUiState,
                        onItemValueChange = { newValue -> artistsViewModel.updateNewUiState(newValue) },
                        onSaveClick = {
                            desktopUiViewModel.setDialogContent(DialogContent.ARTIST_FORM)
                            artistsViewModel.saveArtist()
                        },
                        onCancelClick = {
                            desktopUiViewModel.setDialogContent(DialogContent.NONE)

                        }
                    )

                    DialogContent.CHARACTER_FORM -> CharacterForm(
                        charactersViewModel.newCharacterUiState,
                        onItemValueChange = { newValue ->
                            charactersViewModel.updateNewUiState(
                                newValue
                            )
                        },
                        onSaveClick = {
                            desktopUiViewModel.setDialogContent(DialogContent.CHARACTER_FORM)
                            charactersViewModel.saveCharacter()
                        },
                        onCancelClick = {
                            desktopUiViewModel.setDialogContent(DialogContent.NONE)
                        }
                    )

                    DialogContent.TAG_FORM -> TODO()
                }
            }
        }
    }
}