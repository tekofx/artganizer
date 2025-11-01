package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.managers.UiStateManager
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.components.forms.DesktopSubmissionForm
import dev.tekofx.artganizer.ui.components.layout.DesktopLayout
import dev.tekofx.artganizer.ui.components.layout.LeftPanel
import dev.tekofx.artganizer.ui.components.layout.RightArtistPanel
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
) {
    val uiStateManager = koinInject<UiStateManager>()
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()
    val charactersViewModel = koinViewModel<CharactersViewModel>()
    val tagsViewModel = koinViewModel<TagsViewModel>()

    val submissions by submissionsViewModel.submissions.collectAsState()
    val files by uiStateManager.files.collectAsState()
    val currentImageIndex by submissionsViewModel.currentImageIndex.collectAsState()

    val scope = rememberCoroutineScope()
    var isEnabled by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }


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

                DesktopSubmissionForm(
                    uris = files.map { it.path },
                    artistsViewModel = artistsViewModel,
                    submissionDetails = submissionsViewModel.newSubmissionDetails,
                    charactersViewModel = charactersViewModel,
                    tagsViewModel = tagsViewModel,
                    currentImageIndex = currentImageIndex,
                    onItemValueChange = { newValue -> submissionsViewModel.updateNewUiState(newValue) },
                    onSaveClick = {
                        scope.launch {
                            submissionsViewModel.saveSubmission()
                        }
                        showDialog = false
                    },
                    onCancelClick = {
                        submissionsViewModel.clearNewUiState()
                        showDialog = false
                    }
                )
            }
        }
    }

    DesktopLayout(
        leftPanel = {
            LeftPanel(
                onArtistClick = {
                    artistsViewModel.getArtistWithSubmissions(it)
                },
                onSubmissionAddClick = {
                    scope.launch {
                        val files = FileKit.openFilePicker(
                            mode = FileKitMode.Multiple(),
                            type = FileKitType.Image
                        )

                        files?.let {
                            uiStateManager.files.value = files
                            showDialog = true
                        }
                    }
                }
            )
        },
        rightPanel = {
            if (isEnabled) {
                RightArtistPanel()
            }
        }
    ) {
        ArtistForm(
            artistUiState = artistsViewModel.newArtistUiState,
            onItemValueChange = { newValue -> artistsViewModel.updateNewUiState(newValue) },
            onSaveClick = {
                scope.launch {
                    artistsViewModel.saveArtist()
                    navController.popBackStack()
                }
            },
            onCancelClick = {
                navController.popBackStack()
            }
        )

    }

}

