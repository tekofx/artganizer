package dev.tekofx.artganizer.ui.screens.tags

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.tags.TagForm
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import kotlinx.coroutines.launch


@Composable
fun TagCreationScreen(
    navHostController: NavHostController,
    tagsViewModel: TagsViewModel = viewModel<TagsViewModel>(),
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        tagsViewModel.clearNewUiState()
    }
    TagForm(
        tagsViewModel.newTagUiState,
        onItemValueChange = { newValue -> tagsViewModel.updateNewUiState(newValue) },
        onSaveClick = {
            navHostController.popBackStack()
            scope.launch { tagsViewModel.saveTag() }
        },
        onCancelClick = {
            navHostController.popBackStack()
        }
    )
}