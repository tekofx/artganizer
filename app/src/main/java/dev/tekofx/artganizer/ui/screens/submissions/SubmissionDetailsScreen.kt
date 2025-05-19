package dev.tekofx.artganizer.ui.screens.submissions

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.PaletteColorList
import dev.tekofx.artganizer.ui.components.SmallCard
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.submission.Rating
import dev.tekofx.artganizer.ui.components.submission.SubmissionViewer
import dev.tekofx.artganizer.ui.components.submission.SubmissionsForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.toSubmissionWithArtist
import dev.tekofx.artganizer.utils.dateToString
import dev.tekofx.artganizer.utils.formatFileSize
import kotlinx.coroutines.launch

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
    val submission = submissionsViewModel.currentSubmissionDetails.toSubmissionWithArtist()
    val scope = rememberCoroutineScope()

    val currentImage by submissionsViewModel.currentImageIndex.collectAsState()


    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                submissionsViewModel.setShowPopup(true)
                submissionsViewModel.deleteSubmission(
                    context,
                    submission
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
                submissionsViewModel.uris,
                artistsViewModel,
                submissionsViewModel.editingSubmissionDetails,
                onItemValueChange = {
                    submissionsViewModel.updateEditingUiState(it)
                },
                onSaveClick = {
                    scope.launch { submissionsViewModel.editSubmission() }
                    submissionsViewModel.setShowEditSubmission(false)
                },
                onCancelClick = {
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
                },
                onImageChange = {
                    submissionsViewModel.setCurrentImage(it)
                },
                currentImage = currentImage
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
    currentImage: Int,
    onImageChange: (Int) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (submission.images.isNotEmpty()) {
            SubmissionViewer(
                submission.submission.title,
                submission.images.map { it.uri },
                currentImage,
                onImageChange = { onImageChange(it) }
            )
        }
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
            Rating(submission.submission.rating)
        }

        if (submission.artist != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Artist",
                    textAlign = TextAlign.Center,
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

        if (submission.images.isNotEmpty()) {

            ImageInfo(submission.images[currentImage])
        }
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
fun ImageInfo(image: Image) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PaletteColorList(image.palette)

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
                Text(dateToString(image.date))
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
                Text(formatFileSize(image.size))
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

