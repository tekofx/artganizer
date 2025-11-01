package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.x
import dev.tekofx.artganizer.managers.DialogContent
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
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
) {
    val desktopUiViewModel = koinInject<DesktopUiViewModel>()
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()
    val charactersViewModel = koinViewModel<CharactersViewModel>()
    val tagsViewModel = koinViewModel<TagsViewModel>()

    val submissions by submissionsViewModel.submissions.collectAsState()
    val files by submissionsViewModel.newFiles.collectAsState()
    val currentImageIndex by submissionsViewModel.currentImageIndex.collectAsState()

    val scope = rememberCoroutineScope()
    val showDialog by desktopUiViewModel.showDialog.collectAsState()
    val showRigthPanel by desktopUiViewModel.showRightPanel.collectAsState()
    val dialogContent by desktopUiViewModel.dialogContent.collectAsState()

    if (showDialog) {
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
                            scope.launch {
                                submissionsViewModel.saveSubmission()
                            }
                            desktopUiViewModel.toggleDialog()
                        },
                        onCancelClick = {
                            submissionsViewModel.clearNewUiState()
                            desktopUiViewModel.toggleDialog()
                        }
                    )

                    DialogContent.ARTIST_FORM -> ArtistForm(
                        artistUiState = artistsViewModel.newArtistUiState,
                        onItemValueChange = { newValue -> artistsViewModel.updateNewUiState(newValue) },
                        onSaveClick = {
                            desktopUiViewModel.toggleDialog()
                            artistsViewModel.saveArtist()
                        },
                        onCancelClick = {
                            desktopUiViewModel.toggleDialog()
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
                            desktopUiViewModel.toggleDialog()
                            charactersViewModel.saveCharacter()
                        },
                        onCancelClick = {
                            desktopUiViewModel.toggleDialog()
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
                    desktopUiViewModel.toggleRightPanel()
                },
                onSubmissionAddClick = {
                    scope.launch {
                        val files = FileKit.openFilePicker(
                            mode = FileKitMode.Multiple(),
                            type = FileKitType.Image
                        )

                        files?.let {
                            submissionsViewModel.newFiles.value = files
                            desktopUiViewModel.setDialogContent(DialogContent.SUBMISSIONS_FORM)
                            desktopUiViewModel.toggleDialog()
                        }
                    }
                },
                onArtistAddClick = {
                    desktopUiViewModel.setDialogContent(DialogContent.ARTIST_FORM)
                    desktopUiViewModel.toggleDialog()

                },
                onCharacterAddClick = {
                    desktopUiViewModel.setDialogContent(DialogContent.CHARACTER_FORM)
                    desktopUiViewModel.toggleDialog()

                },
                onTagAddClick = {}
            )
        },
        rightPanel = {
            if (showRigthPanel) {
                Column {
                    IconButton(
                        onClick = { desktopUiViewModel.toggleRightPanel() }
                    ) {
                        Icon(
                            painterResource(Res.drawable.x),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Close Right Panel"
                        )
                    }
                    RightArtistPanel()
                }
            }
        }
    ) {
        Text("Main panel")

    }

}

