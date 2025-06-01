package dev.tekofx.artganizer.ui.screens.tags

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.tags.TagForm
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import kotlinx.coroutines.launch


@Composable
fun TagCreationScreen(
    navHostController: NavHostController,
    tagViewModel: TagsViewModel,
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        tagViewModel.clearNewUiState()
    }
    TagForm(
        tagViewModel.newTagUiState,
        onItemValueChange = { newValue -> tagViewModel.updateNewUiState(newValue) },
        onSaveClick = {
            navHostController.popBackStack()
            scope.launch { tagViewModel.saveTag() }
        },
        onCancelClick = {
            navHostController.popBackStack()
        }
    )
}