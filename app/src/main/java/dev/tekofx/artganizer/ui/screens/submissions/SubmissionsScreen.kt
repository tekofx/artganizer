package dev.tekofx.artganizer.ui.screens.submissions

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
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
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.GalleryBottomSheet
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.submission.InteractiveGallery
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import kotlinx.coroutines.launch


fun List<SubmissionWithArtist>.toSubmissions(): List<Submission> {
    return this.map { it.submission }
}


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

    Log.d("SubmissionsScreen", submissions.toString())

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri>? ->
            uris?.let {
                if (uris.isNotEmpty()) {
                    scope.launch {
                        submissionsViewModel.setUris(uris)
                        submissionsViewModel.updateNewUiState(SubmissionDetails())
                        navHostController.navigate(NavigateDestinations.SUBMISSION_CREATION_SCREEN)
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
            floatingActionButton = {
                CreateFab(
                    onClick = { launcher.launch("image/*") })
            }) {
            InteractiveGallery(
                submissions.toSubmissions(),
                onImageClick = {
                    navHostController.navigate("${NavigateDestinations.SUBMISSIONS_SCREEN}/${it}")
                }
            )
        }
    }
}




