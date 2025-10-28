package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun SubmissionsScreen() {
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()

    val submissions by submissionsViewModel.submissions.collectAsState()


    Text("Submissions Screen - Android")
    Text(submissions.submissions.size.toString())
    submissions.submissions.forEach {
        Text(it.submission.title)
    }
}