package dev.tekofx.artganizer.ui.screens.submissions

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.PaletteColorList
import dev.tekofx.artganizer.ui.components.SmallCard
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.submission.Rating
import dev.tekofx.artganizer.ui.components.submission.SubmissionsForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.toSubmissionWithArtist
import dev.tekofx.artganizer.utils.dateToString
import kotlinx.coroutines.launch
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubmissionDetailsScreen(
    submissionsViewModel: SubmissionsViewModel,
    artistsViewModel: ArtistsViewModel,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val showPopup by submissionsViewModel.showPopup.collectAsState()
    val showEdit by submissionsViewModel.showEditSubmission.collectAsState()
    val submission = submissionsViewModel.currentSubmissionUiState.toSubmissionWithArtist()
    val scope = rememberCoroutineScope()

    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                submissionsViewModel.setShowPopup(true)
                submissionsViewModel.deleteSubmission(
                    context,
                    submission.submission
                )
                navHostController.popBackStack()
                submissionsViewModel.setShowPopup(false)
            },
            onDismiss = {
                submissionsViewModel.setShowPopup(false)
            }
        )
    }
    Scaffold {
        if (showEdit) {
            SubmissionsForm(
                artistsViewModel,
                submissionsViewModel.currentSubmissionUiState,
                onItemValueChange = {
                    submissionsViewModel.updateCurrentUiState(it)
                },
                onSaveClick = {
                    scope.launch { submissionsViewModel.editSubmission() }
                    submissionsViewModel.setShowEditSubmission(false)
                }
            )

        } else {
            SubmissionInfo(
                submission,
                onArtistCardClick = { artistId ->
                    Log.d("SubmissionDetailsScreen", "Artist ID: $artistId")
                    navHostController.navigate("${NavigateDestinations.ARTISTS_SCREEN}/$artistId")
                },
                onDelete = {
                    submissionsViewModel.setShowPopup(true)
                },
                onEdit = {
                    submissionsViewModel.setShowEditSubmission(true)
                }
            )
        }
    }
}


@Composable
fun SubmissionInfo(
    submission: SubmissionWithArtist,
    onArtistCardClick: (Int) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(submission.submission.imagePath)
                .build(),
            contentDescription = submission.submission.title,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        if (submission.submission.title.isNotEmpty()) {
            Text(
                text = submission.submission.title,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        if (submission.submission.description.isNotEmpty()) {

            Text(submission.submission.description)
        }
        if (submission.submission.rating > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Rating",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Rating(submission.submission.rating)
            }
        }

        if (submission.artist != null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    "Artist",
                    style = MaterialTheme.typography.headlineSmall,
                )
                SmallCard(
                    title = submission.artist.name,
                    imagePath = submission.artist.imagePath,
                    onClick = {
                        onArtistCardClick(submission.artist.id)
                    }
                )
            }
        }

        PaletteColorList(submission.submission.palette)

        ImageInfo(submission.submission)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ButtonWithIcon(
                iconResource = IconResource.fromDrawableResource(R.drawable.edit),
                onClick = { onEdit() },
                text = "Edit"
            )
            ButtonWithIcon(
                iconResource = IconResource.fromDrawableResource(R.drawable.trash),
                onClick = {
                    onDelete()
                },
                text = "Delete",
                color = MaterialTheme.colorScheme.error
            )
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
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                IconResource.fromDrawableResource(R.drawable.file_info).asPainterResource(),
                contentDescription = "",
            )
            Text("File Info")
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
                    IconResource.fromDrawableResource(R.drawable.calendar_outlined)
                        .asPainterResource(),
                    contentDescription = ""
                )
                Text(dateToString(submission.date))
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
        extension = "JPEG",
        artistId = 1,
    )

    ImageInfo(submission)
}