package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.components.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.artists.ArtistForm
import dev.tekofx.artganizer.ui.components.artists.SocialNetworks
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.submission.Gallery
import dev.tekofx.artganizer.ui.utils.AVATAR_SIZE
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.artists.toArtistWithSubmissions
import kotlinx.coroutines.launch

@Composable
fun ArtistDetailsScreen(
    artistsViewModel: ArtistsViewModel,
    navHostController: NavHostController
) {
    val showPopup by artistsViewModel.showPopup.collectAsState()
    val showEditArtist by artistsViewModel.showEditArtist.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
    Scaffold { paddingValues ->
        if (showEditArtist) {
            ArtistForm(
                artistsViewModel.currentArtistUiState,
                onItemValueChange = { newValue -> artistsViewModel.updateCurrentUiState(newValue) },
                onSaveClick = {
                    scope.launch { artistsViewModel.editArtist(context) }
                    artistsViewModel.setShowEditArtist(false)
                },
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {


                    Avatar(
                        artistsViewModel.currentArtistUiState.toArtistWithSubmissions().artist.imagePath,
                        artistsViewModel.currentArtistUiState.toArtistWithSubmissions().artist.name,
                        size = AVATAR_SIZE
                    )
                }
                item {

                    Text(
                        text = artistsViewModel.currentArtistUiState.toArtistWithSubmissions().artist.name,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                item {

                    SocialNetworks(artistsViewModel.currentArtistUiState.toArtistWithSubmissions().artist.socialNetworks)
                }
                item {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ButtonWithIcon(
                            onClick = { artistsViewModel.setShowEditArtist(true) },
                            text = "Edit",
                            iconResource = IconResource.fromDrawableResource(R.drawable.edit),
                        )
                        ButtonWithIcon(
                            onClick = {
                                artistsViewModel.setShowPopup(true)
                            },
                            text = "Delete",
                            iconResource = IconResource.fromDrawableResource(R.drawable.trash),
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp) // Set a fixed height for the grid
                    ) {
                        Gallery(
                            artistsViewModel.currentArtistUiState.toArtistWithSubmissions().submissions,
                            onImageClick = {
                                navHostController.navigate("${NavigateDestinations.SUBMISSIONS_SCREEN}/${it}")
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ImageInfo(image: Image) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    IconResource.fromDrawableResource(R.drawable.calendar_outlined)
                        .asPainterResource(),
                    contentDescription = ""
                )
                Text(image.date.toString())
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(image.dimensions)
                Icon(
                    IconResource.fromDrawableResource(R.drawable.maximize_outlined)
                        .asPainterResource(),
                    contentDescription = ""
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    IconResource.fromDrawableResource(R.drawable.device_sd_card_outlined)
                        .asPainterResource(),
                    contentDescription = ""
                )
                Text("${image.size} MB")
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(image.extension)
                Icon(
                    IconResource.fromDrawableResource(R.drawable.file_outlined)
                        .asPainterResource(),
                    contentDescription = ""
                )
            }
        }
    }
}

