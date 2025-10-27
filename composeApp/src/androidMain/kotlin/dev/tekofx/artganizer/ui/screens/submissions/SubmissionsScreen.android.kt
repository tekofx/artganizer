package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.filter_outlined
import dev.tekofx.artganizer.navigation.AppRoute
import dev.tekofx.artganizer.navigation.NavigationViewModel
import dev.tekofx.artganizer.ui.layout.BottomAppBarScaffold
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun SubmissionsScreen() {
    val navigationViewModel = koinViewModel<NavigationViewModel>()
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()
    val scope = rememberCoroutineScope()

    val submissions by submissionsViewModel.submissions.collectAsState()
    BottomAppBarScaffold(
        textFieldState = rememberTextFieldState(),
        onFocusChanged = {},
        onAddButtonClick = {
            scope.launch {
                val imageFiles =
                    FileKit.openFilePicker(type = FileKitType.Image, mode = FileKitMode.Multiple())
                val imagePaths = imageFiles?.map { it.path }
                submissionsViewModel.setUris(imagePaths!!)
                navigationViewModel.navigateTo(AppRoute.SubmissionCreation)
            }
        },
        firstButtonIcon = Res.drawable.filter_outlined,
    ) {

        Text("Submissions Screen - Android")
        Text(submissions.submissions.size.toString())
        submissions.submissions.forEach {
            Text(it.submission.title)
        }
    }
}