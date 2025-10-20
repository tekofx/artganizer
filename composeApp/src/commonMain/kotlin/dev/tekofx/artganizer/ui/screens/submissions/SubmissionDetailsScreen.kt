package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel

@Composable
expect fun SubmissionDetailsScreen(
    submissionId: Long,
    navHostController: NavHostController,
)