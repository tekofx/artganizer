package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.deselect
import artganizer.app.generated.resources.edit
import artganizer.app.generated.resources.select_all
import artganizer.app.generated.resources.trash
import artganizer.app.generated.resources.x
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.GalleryBottomSheet
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.submission.InteractiveGallery
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionsScreen(
    navHostController: NavHostController,
    viewModel: SubmissionsViewModel = viewModel<SubmissionsViewModel>()
) {
    // Data
    val submissions by viewModel.submissions.collectAsState()

    // Ui
    val showPopup by viewModel.showPopup.collectAsState()
    val isSelecting by viewModel.isSelecting.collectAsState()


    // States
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val scope = rememberCoroutineScope()


    /*val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri>? ->
            uris?.let {
                if (uris.isNotEmpty()) {
                    scope.launch {
                        viewModel.setUris(uris)
                        viewModel.updateNewUiState(SubmissionDetails())
                        navHostController.navigate(NavigateDestinations.SUBMISSION_CREATION)
                    }
                }
            }
        }
    )*/

    BottomSheetScaffold(
        scaffoldState = scaffoldState, sheetContent = {
            GalleryBottomSheet()
        }) {
        Scaffold(
            modifier = Modifier.padding(10.dp),
            floatingActionButton = {
                if (submissions.selectedSubmissions.isEmpty()) {
                    CreateFab(
                        onClick = {
                            //launcher.launch("image/*")
                        },
                        icon = Res.drawable.edit
                    )
                }
            },
            bottomBar = {
                if (submissions.selectedSubmissions.isNotEmpty()) {
                    SelectionBar(
                        selectedSubmissions = submissions.selectedSubmissions.size,
                        scope = scope,
                        onRemoveClick = { viewModel.setShowPopup(true) },
                        onSelectAllClick = viewModel::selectAll,
                        onDeselectAllClick = viewModel::deselectAll,
                        onClearSelection = viewModel::clearSelectedSubmissions
                    )
                }
            }
        ) {
            if (showPopup) {
                ConfirmationPopup(
                    title = "Remove ${submissions.selectedSubmissions.size} Submissions",
                    message = "Are you sure you want to proceed?",
                    onConfirm = {
                        viewModel.deleteSelectedSubmissions()
                        viewModel.setShowPopup(false)
                    },
                    onDismiss = {
                        viewModel.setShowPopup(false)
                    }
                )
            }
            InteractiveGallery(
                submissions,
                isSelecting,
                onImageClick = {
                    navHostController.navigate("${NavigateDestinations.SUBMISSION_DETAILS}/${it}")
                },
                onSelectImage = {
                    viewModel.onSelectSubmission(it)
                }
            )
        }
    }
}


@Composable
fun SelectionBar(
    selectedSubmissions: Int,
    scope: CoroutineScope,
    onRemoveClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    onDeselectAllClick: () -> Unit,
    onClearSelection: () -> Unit
) {
    Card(
        modifier = Modifier.padding(10.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            onClearSelection()
                        }
                    }
                ) {
                    Icon(
                        painterResource(Res.drawable.x),
                        contentDescription = ""
                    )
                }
                IconButton(
                    onClick = onSelectAllClick
                ) {
                    Icon(
                        painterResource(Res.drawable.select_all),
                        contentDescription = ""
                    )
                }
                IconButton(
                    onClick = onDeselectAllClick
                ) {
                    Icon(
                        painterResource(Res.drawable.deselect),
                        contentDescription = ""
                    )
                }
            }

            Text("Selected: $selectedSubmissions", style = MaterialTheme.typography.titleMedium)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start),
            ) {
                IconButton(
                    onClick = {
                    }
                ) {
                    Icon(
                        painterResource(Res.drawable.edit), contentDescription = ""
                    )
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            onRemoveClick()
                        }
                    }
                ) {
                    Icon(
                        painterResource(Res.drawable.trash), contentDescription = "",
                        tint = Color(0xFFB00020)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SelectionCardPreview() {

    SelectionBar(
        selectedSubmissions = 5,
        scope = rememberCoroutineScope(),
        onRemoveClick = {},
        onSelectAllClick = {},
        onClearSelection = {},
        onDeselectAllClick = {}
    )
}
