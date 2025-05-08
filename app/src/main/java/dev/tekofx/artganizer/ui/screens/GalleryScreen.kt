package dev.tekofx.artganizer.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.GalleryBottomSheet
import dev.tekofx.artganizer.ui.components.GalleryItem
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionDetails
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

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                // Save the image and navigate to the next screen
                val path = getPathFromUri(context, uri)
                if (path != null) {
                    scope.launch {
                        submissionsViewModel.setNewSubmissionDetails(
                            SubmissionDetails(
                                title = "",
                                description = "",
                                imagePath = path
                            )
                        )
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
                ExtendedFloatingActionButton(onClick = {
                    launcher.launch("image/*")
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
            LazyColumn(
                state = listState
            ) {
                items(submissions, key = { it.id }) {
                    GalleryItem(it)
                }
            }
            /*Text("Gallery Screen Content")
            ImageSelectionScreen()*/
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


fun saveImageToInternalStorage(context: Context, uri: Uri): String {
    val uniqueFilename = "${UUID.randomUUID()}.jpg"
    val inputStream = context.contentResolver.openInputStream(uri)
    val outputStream = context.openFileOutput(uniqueFilename, Context.MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return context.filesDir.resolve(uniqueFilename).absolutePath
}

fun getPathFromUri(context: Context, uri: Uri): String? {
    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        val fileName = it.getString(nameIndex)
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = context.filesDir.resolve(fileName)
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        file.absolutePath
    }
}