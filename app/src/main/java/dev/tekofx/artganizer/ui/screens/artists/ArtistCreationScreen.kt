package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.artists.ArtistForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import kotlinx.coroutines.launch


@Composable
fun ArtistCreationScreen(artistsViewModel: ArtistsViewModel, navHostController: NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ArtistForm(
            artistsViewModel.newArtistUiState,
            onItemValueChange = { newValue -> artistsViewModel.updateNewUiState(newValue) },
            onSaveClick = {
                navHostController.popBackStack()
                scope.launch { artistsViewModel.saveArtist(context) }
            },
        )

    }
}