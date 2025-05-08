package dev.tekofx.artganizer.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.GalleryBottomSheet
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    navHostController: NavHostController,
    submissionsViewModel: SubmissionsViewModel
) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState, sheetContent = {
            GalleryBottomSheet()
        }) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(onClick = {
                    scope.launch { navHostController.navigate(NavigateDestinations.SUBMISSION_CREATION_SCREEN) }
                }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            IconResource.fromImageVector(Icons.Filled.Add)
                                .asPainterResource(), contentDescription = ""
                        )
                        Text("Add")
                    }
                }
            }) {
            Column {
                Text("Gallery Screen Content")
                ImageSelectionScreen()
            }
        }
    }
}

@Composable
fun ImageSelectionScreen() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { saveImageToInternalStorage(context, it) }
        }
    )

    Button(onClick = { launcher.launch("image/*") }) {
        Text(text = "Select Image")
    }
}


fun saveImageToInternalStorage(context: Context, uri: Uri) {
    val uniqueFilename = "${UUID.randomUUID()}.jpg"
    val inputStream = context.contentResolver.openInputStream(uri)
    val outputStream = context.openFileOutput(uniqueFilename, Context.MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
}

