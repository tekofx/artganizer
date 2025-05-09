package dev.tekofx.artganizer.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.Gallery
import dev.tekofx.artganizer.ui.components.GalleryBottomSheet
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    navHostController: NavHostController, submissionsViewModel: SubmissionsViewModel
) {

    // Data
    val submissions by submissionsViewModel.submissions.collectAsState()
    // States
    val listState = rememberLazyListState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), onResult = { uri: Uri? ->
            uri?.let {
                // Save the image and navigate to the next screen
                scope.launch {
                    submissionsViewModel.setNewSubmissionDetails(
                        SubmissionDetails(
                            title = "", description = "", imagePath = uri
                        )
                    )
                    navHostController.navigate(NavigateDestinations.SUBMISSION_CREATION_SCREEN)
                }
            }
        })

    BottomSheetScaffold(
        scaffoldState = scaffoldState, sheetContent = {
            GalleryBottomSheet()
        }) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(onClick = {
                    launcher.launch("image/*")
                }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            IconResource.fromImageVector(Icons.Filled.Add).asPainterResource(),
                            contentDescription = ""
                        )
                        Text("Add")
                    }
                }
            }) {
            Gallery(navHostController, submissions)
        }
    }
}




