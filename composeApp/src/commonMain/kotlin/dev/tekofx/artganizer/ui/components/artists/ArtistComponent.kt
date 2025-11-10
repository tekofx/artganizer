package dev.tekofx.artganizer.ui.components.artists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.ArtistDetailsRoute
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.components.ArtistInfo
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistDetails
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.artists.toArtistWithSubmissions
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistComponent(
    artist: ArtistDetailsRoute,
    navController: NavHostController
) {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val showPopup by artistsViewModel.showDeletePopup.collectAsState()
    val showEditArtist by artistsViewModel.showEditArtist.collectAsState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        artistsViewModel.getArtistWithSubmissions(artist.id)
    }

    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                artistsViewModel.setShowDeletePopup(true)
                artistsViewModel.deleteArtist(artistsViewModel.currentArtistUiState)
                artistsViewModel.setShowDeletePopup(false)
                artistsViewModel.updateCurrentUiState(ArtistDetails())
                navController.popBackStack()
            },
            onDismiss = {
                artistsViewModel.setShowDeletePopup(false)
            }
        )
    }
    if (showEditArtist) {
        ArtistForm(
            artistsViewModel.currentArtistUiState,
            onItemValueChange = { newValue -> artistsViewModel.updateCurrentUiState(newValue) },
            onSaveClick = {
                scope.launch { artistsViewModel.editArtist() }
                artistsViewModel.setShowEditArtist(false)
            },
            onCancelClick = {
                artistsViewModel.setShowEditArtist(false)
            },
        )
    } else {
        ArtistInfo(
            artistWithSubmissions = artistsViewModel.currentArtistUiState.toArtistWithSubmissions(),
            onEditClick = {
                artistsViewModel.setShowEditArtist(true)
            },
            onDeleteClick = {
                artistsViewModel.setShowDeletePopup(true)
            },
        )
    }
}