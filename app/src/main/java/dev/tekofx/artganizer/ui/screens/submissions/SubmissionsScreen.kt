package dev.tekofx.artganizer.ui.screens.submissions

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.getActivityViewModel
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.GalleryBottomSheet
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.submission.InteractiveGallery
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionsScreen(
    navHostController: NavHostController,
    viewModel: SubmissionsViewModel = getActivityViewModel<SubmissionsViewModel>()
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
    val context = LocalContext.current


    val launcher = rememberLauncherForActivityResult(
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
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState, sheetContent = {
            GalleryBottomSheet()
        }) {
        Scaffold(
            modifier = Modifier.padding(10.dp),
            floatingActionButton = {
                if (submissions.selectedSubmissions.isEmpty()) {
                    CreateFab(
                        onClick = { launcher.launch("image/*") }
                    )
                }
            },
            bottomBar = {
                if (submissions.selectedSubmissions.isNotEmpty()) {
                    SelectionCard(
                        selectedSubmissions = submissions.selectedSubmissions.size,
                        scope = scope,
                        onRemoveClick = { viewModel.setShowPopup(true) },
                        onSelectAllClick = viewModel::selectAll,
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
                        viewModel.deleteSelectedSubmissions(context)
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
fun SelectionCard(
    selectedSubmissions: Int,
    scope: CoroutineScope,
    onRemoveClick: () -> Unit,
    onSelectAllClick: () -> Unit,
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
                        IconResource.fromDrawableResource(R.drawable.x)
                            .asPainterResource(), contentDescription = ""
                    )
                }
                IconButton(
                    onClick = onSelectAllClick
                ) {
                    Icon(
                        IconResource.fromDrawableResource(R.drawable.select_all)
                            .asPainterResource(), contentDescription = ""
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            ) {
                Icon(
                    IconResource.fromDrawableResource(R.drawable.gallery_outlined)
                        .asPainterResource(), contentDescription = ""
                )
                Text("Selected: $selectedSubmissions")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start),
            ) {
                IconButton(
                    onClick = {
                    }
                ) {
                    Icon(
                        IconResource.fromDrawableResource(R.drawable.edit)
                            .asPainterResource(), contentDescription = ""
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
                        IconResource.fromDrawableResource(R.drawable.trash)
                            .asPainterResource(), contentDescription = "",
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

    SelectionCard(
        selectedSubmissions = 5,
        scope = rememberCoroutineScope(),
        onRemoveClick = {},
        onSelectAllClick = {},
        onClearSelection = {}
    )
}
