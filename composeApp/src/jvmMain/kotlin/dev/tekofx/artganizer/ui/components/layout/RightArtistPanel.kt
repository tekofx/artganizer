package dev.tekofx.artganizer.ui.components.layout

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dev.tekofx.artganizer.navigation.ArtistDetailsRoute
import dev.tekofx.artganizer.ui.components.artists.ArtistComponent
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RightArtistPanel() {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val currentArtist = artistsViewModel.currentArtistUiState
    val navController = rememberNavController()

    if (currentArtist.isEntryValid) {
        ArtistComponent(
            ArtistDetailsRoute(currentArtist.artistDetails.id),
            navController = navController
        )
    }

}