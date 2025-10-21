package dev.tekofx.artganizer.ui.screens.artists

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import dev.tekofx.artganizer.navigation.AppRoute
import dev.tekofx.artganizer.navigation.NavigationViewModel
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.components.ArtistInfo
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.artists.toArtistWithSubmissions
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
actual fun ArtistDetailsScreen(artist: AppRoute.ArtistDetails) {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val navigationViewModel = koinViewModel<NavigationViewModel>()
    val showPopup by artistsViewModel.showPopup.collectAsState()
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
                artistsViewModel.setShowPopup(true)
                artistsViewModel.deleteArtist(artistsViewModel.currentArtistUiState)
                navigationViewModel.navigateBack()
                artistsViewModel.setShowPopup(false)
            },
            onDismiss = {
                artistsViewModel.setShowPopup(false)
            }
        )
    }
    Scaffold {
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
                    artistsViewModel.setShowPopup(true)
                },
                onImageClick = { submissionId ->
                    navigationViewModel.navigateTo(
                        AppRoute.SubmissionDetails(submissionId)
                    )
                },
            )
        }
    }
}