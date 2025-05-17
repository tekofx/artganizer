package dev.tekofx.artganizer.ui.screens.submissions

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.GalleryBottomSheet
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.submission.Gallery
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionUiState
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionsScreen(
    navHostController: NavHostController, submissionsViewModel: SubmissionsViewModel
) {

    // Data
    val submissions by submissionsViewModel.submissions.collectAsState()
    // States
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri>? ->
            uris?.let {
                // Process each selected image
                scope.launch {
                    submissionsViewModel.updateNewSubmissionsUiState(SubmissionUiState(imagePaths = uris))
                    navHostController.navigate(NavigateDestinations.SUBMISSION_CREATION_SCREEN)
                }
            }
        }
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState, sheetContent = {
            GalleryBottomSheet()
        }) {
        Scaffold(
            floatingActionButton = {
                CreateFab(
                    onClick = { launcher.launch("image/*") })
            }) {
            Gallery(navHostController, submissions)
        }
    }
}




