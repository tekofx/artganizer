package dev.tekofx.artganizer.ui.screens.tags

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel

@Composable
expect fun TagDetailsScreen(
    tagId: Long,
    navHostController: NavHostController,
)