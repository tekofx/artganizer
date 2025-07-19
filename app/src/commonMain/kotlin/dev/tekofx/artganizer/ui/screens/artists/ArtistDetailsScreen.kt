package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.edit
import artganizer.app.generated.resources.trash
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.components.artists.ArtistForm
import dev.tekofx.artganizer.ui.components.artists.SocialNetworks
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.submission.GalleryRow
import dev.tekofx.artganizer.ui.utils.AVATAR_SIZE
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.artists.toArtistWithSubmissions
import kotlinx.coroutines.launch

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistDetailsScreen(
    artistId: Long,
    navHostController: NavHostController,
    artistsViewModel: ArtistsViewModel = viewModel(),
) {
    val showPopup by artistsViewModel.showPopup.collectAsState()
    val showEditArtist by artistsViewModel.showEditArtist.collectAsState()
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        artistsViewModel.getArtistWithSubmissions(artistId)
    }

    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                artistsViewModel.setShowPopup(true)
                artistsViewModel.deleteArtist(artistsViewModel.currentArtistUiState)
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

@Composable
fun ArtistInfo(
    artistWithSubmissions: ArtistWithSubmissions,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onImageClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            Avatar(
                artistWithSubmissions.artist.imagePath,
                size = AVATAR_SIZE
            )
        }
        item {
            Text(
                text = artistWithSubmissions.artist.name,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            SocialNetworks(artistWithSubmissions.artist.socialNetworks)
        }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ButtonWithIcon(
                    onClick = onEditClick,
                    text = "Edit",
                    icon = Res.drawable.edit,
                )
                ButtonWithIcon(
                    onClick = onDeleteClick,
                    text = "Delete",
                    icon = Res.drawable.trash,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

        // Group submissions into chunks of 3
        artistWithSubmissions.submissions.chunked(3).forEach { chunk ->
            item {
                GalleryRow(
                    submissions = chunk,
                    onImageClick = onImageClick
                )
            }
        }
    }
}

