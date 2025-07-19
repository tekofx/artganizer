package dev.tekofx.artganizer.ui.screens.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.characters.CharacterForm
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import kotlinx.coroutines.launch


@Composable
fun CharacterCreationScreen(
    navHostController: NavHostController,
    charactersViewModel: CharactersViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        charactersViewModel.clearNewUiState()
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CharacterForm(
            charactersViewModel.newCharacterUiState,
            onItemValueChange = { newValue -> charactersViewModel.updateNewUiState(newValue) },
            onSaveClick = {
                navHostController.popBackStack()
                scope.launch { charactersViewModel.saveCharacter() }
            },
            onCancelClick = {
                navHostController.popBackStack()
            }
        )
    }
}