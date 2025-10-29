package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.tekofx.artganizer.ui.components.AndroidGallery
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SubmissionsScreen(
    onSubmissionClick: (Long) -> Unit,
) {
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()

    val submissions by submissionsViewModel.submissions.collectAsState()

    Column {


        Text("Submissions Screen - Android")
        AndroidGallery(
            submissions = submissions,
            isSelecting = false,
            onImageClick = onSubmissionClick,
            onSelectImage = {}
        )
    }
}