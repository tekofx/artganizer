package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.managers.DialogContent
import dev.tekofx.artganizer.managers.RightPanelContent
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.components.forms.DesktopSubmissionForm
import dev.tekofx.artganizer.ui.components.input.form.CharacterForm
import dev.tekofx.artganizer.ui.components.layout.DesktopLayout
import dev.tekofx.artganizer.ui.components.layout.LeftPanel
import dev.tekofx.artganizer.ui.components.layout.RightArtistPanel
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import dev.tekofx.artganizer.viewmodel.DesktopUiViewModel
import io.github.vinceglb.filekit.path
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
) {
    val desktopUiViewModel = koinViewModel<DesktopUiViewModel>()
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()
    val charactersViewModel = koinViewModel<CharactersViewModel>()
    val tagsViewModel = koinViewModel<TagsViewModel>()

    val submissions by submissionsViewModel.submissions.collectAsState()
    val files by submissionsViewModel.newFiles.collectAsState()
    val currentImageIndex by submissionsViewModel.currentImageIndex.collectAsState()


    // Panels show status
    val showLeftPanel by desktopUiViewModel.showLeftPanel.collectAsState()
    val showRightPanel by desktopUiViewModel.showRightPanel.collectAsState()

    val dialogContent by desktopUiViewModel.dialogContent.collectAsState()
    val rightPanelContent by desktopUiViewModel.rightPanelContent.collectAsState()

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

    DesktopLayout(
        leftPanel = {
            LeftPanel(
                onArtistClick = {
                    artistsViewModel.getArtistWithSubmissions(it)
                    desktopUiViewModel.setRightPanelContent(RightPanelContent.ARTIST_DETAILS)
                    if (!showLeftPanel) {
                        desktopUiViewModel.toggleRightPanel()
                    }
                },
                onSubmissionAddClick = {
                    submissionsViewModel.setNewFiles()
                    desktopUiViewModel.setDialogContent(DialogContent.SUBMISSIONS_FORM)
                },
                onArtistAddClick = {
                    desktopUiViewModel.setDialogContent(DialogContent.ARTIST_FORM)
                },
                onCharacterAddClick = {
                    desktopUiViewModel.setDialogContent(DialogContent.CHARACTER_FORM)

                },
                onTagAddClick = {},
            )
        },
        toggleLeftPanelShow = { desktopUiViewModel.toggleLeftPanel() },
        showLeftPanel = showLeftPanel,
        rightPanel = {
            RightArtistPanel()
        },
        toggleRightPanelShow = { desktopUiViewModel.toggleRightPanel() },
        showRightPanel = showRightPanel
    ) {
        Text("Main panel")

    }

}

