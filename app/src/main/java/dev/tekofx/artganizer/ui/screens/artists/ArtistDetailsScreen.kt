package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.components.ConfirmationPopup
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import java.util.Date

@Composable
fun ArtistDetailsScreen(
    artist: Artist,
    artistsViewModel: ArtistsViewModel,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val showPopup by artistsViewModel.showPopup.collectAsState()


    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                artistsViewModel.showPopup()
                artistsViewModel.deleteArtist(context, artist)
                navHostController.popBackStack()
            },
            onDismiss = {
                artistsViewModel.hidePopup()
            }
        )
    }
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Avatar(artist.imagePath, artist.name)
            Text(
                text = artist.name,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Button(onClick = {
                artistsViewModel.showPopup()
            }) {
                Text(text = "Delete")
            }
        }
    }
}


@Composable
fun ImageInfo(submission: Submission) {
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
                Text(submission.date.toString())
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(submission.dimensions)
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
                Text("${submission.sizeInMb} MB")
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(submission.extension)
                Icon(
                    IconResource.fromDrawableResource(R.drawable.file_outlined)
                        .asPainterResource(),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
@Preview
fun ImageInfoPreview() {
    val submission = Submission(
        id = 1,
        title = "Test",
        description = "Test",
        imagePath = "Test",
        rating = 5,
        date = Date(),
        sizeInMb = 1.0,
        dimensions = "1920x1080",
        extension = ".jpg",
        artistId = 1,
    )

    ImageInfo(submission)
}