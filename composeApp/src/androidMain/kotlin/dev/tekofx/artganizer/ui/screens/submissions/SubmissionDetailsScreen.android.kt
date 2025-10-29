package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import coil3.compose.AsyncImage
import dev.tekofx.artganizer.navigation.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SubmissionDetailsScreen(
    submissionDetailsRoute: SubmissionDetails
) {
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()
    val currentSubmission = submissionsViewModel.currentSubmissionDetails

    LaunchedEffect(Unit) {
        submissionsViewModel.getSubmissionWithArtist(submissionDetailsRoute.id)
    }
    Column {
        if (currentSubmission.images.isNotEmpty()) {
            AsyncImage(
                model = currentSubmission.images[0].uri,
                contentDescription = ""
            )
        }

        Text(currentSubmission.title)
        Text(currentSubmission.images.size.toString())
    }
}