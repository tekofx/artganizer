package dev.tekofx.artganizer.ui.screens.artists

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.components.ArtistInfo
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.artists.toArtistWithSubmissions
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
actual fun ArtistDetailsScreen(
    artist: ArtistWithSubmissions,
    navHostController: NavHostController
) {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val showPopup by artistsViewModel.showPopup.collectAsState()
    val showEditArtist by artistsViewModel.showEditArtist.collectAsState()
    val scope = rememberCoroutineScope()


    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                artistsViewModel.setShowPopup(true)
                artistsViewModel.deleteArtist( artistsViewModel.currentArtistUiState)
                navHostController.popBackStack()
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
                    navHostController.navigate(
                        "${NavigateDestinations.SUBMISSIONS_LIST}/$submissionId"
                    )
                },
            )
        }
    }
}