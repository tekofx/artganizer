package dev.tekofx.artganizer.ui.screens.submissions

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.SmallCard
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.submission.FullscreenImageViewer
import dev.tekofx.artganizer.ui.components.submission.PaletteColorList
import dev.tekofx.artganizer.ui.components.submission.Rating
import dev.tekofx.artganizer.ui.components.submission.SubmissionViewer
import dev.tekofx.artganizer.ui.components.submission.SubmissionsForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.toSubmissionWithArtist
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import dev.tekofx.artganizer.utils.dateToString
import dev.tekofx.artganizer.utils.formatFileSize
import dev.tekofx.artganizer.utils.shareImage
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubmissionDetailsScreen(
    navHostController: NavHostController,
    submissionsViewModel: SubmissionsViewModel,
    artistsViewModel: ArtistsViewModel,
    charactersViewModel: CharactersViewModel,
    tagsViewModel: TagsViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Data
    val submission = submissionsViewModel.currentSubmissionDetails.toSubmissionWithArtist()
    val currentImageIndex by submissionsViewModel.currentImageIndex.collectAsState()

    // UI states
    val showPopup by submissionsViewModel.showPopup.collectAsState()
    val showEdit by submissionsViewModel.showEditSubmission.collectAsState()
    val showFullScreen by submissionsViewModel.showFullscreen.collectAsState()

    // Handle back press
    BackHandler(enabled = showFullScreen) {
        submissionsViewModel.setShowFullscreen(false)
    }

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

    if (showFullScreen) {
        if (submission.images.isNotEmpty()) {
            FullscreenImageViewer(
                submission.images[currentImageIndex].uri,
                onClose = { submissionsViewModel.setShowFullscreen(false) }
            )
        }
    } else {
        Scaffold {
            if (showEdit) {
                SubmissionsForm(
                    uris = submissionsViewModel.uris,
                    artistsViewModel = artistsViewModel,
                    charactersViewModel = charactersViewModel,
                    tagsViewModel = tagsViewModel,
                    submissionDetails = submissionsViewModel.editingSubmissionDetails,
                    onItemValueChange = {
                        submissionsViewModel.updateEditingUiState(it)
                    },
                    onSaveClick = {
                        scope.launch { submissionsViewModel.editSubmission() }
                        submissionsViewModel.setShowEditSubmission(false)
                    },
                    onCancelClick = {
                        submissionsViewModel.setShowEditSubmission(false)
                    },
                    currentImageIndex = currentImageIndex
                )
            } else {
                SubmissionInfo(
                    submission,
                    onArtistCardClick = { artistId ->
                        navHostController.navigate("${NavigateDestinations.ARTISTS_SCREEN}/$artistId")
                    },
                    onCharacterCardClick = { characterId ->
                        navHostController.navigate("${NavigateDestinations.CHARACTERS_SCREEN}/$characterId")
                    },
                    onTagCardClick = { tagId ->
                        navHostController.navigate("${NavigateDestinations.TAGS_SCREEN}/$tagId")
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
                    currentImageIndex = currentImageIndex,
                    onImageClick = { submissionsViewModel.setShowFullscreen(true) },
                )
            }
        }
    }
}

@Composable
fun SubmissionInfo(
    submission: SubmissionWithArtist,
    onArtistCardClick: (Long) -> Unit,
    onCharacterCardClick: (Long) -> Unit,
    onTagCardClick: (Long) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    currentImageIndex: Int,
    onImageChange: (Int) -> Unit,
    onImageClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
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
                title = submission.submission.title,
                imagePaths = submission.images.map { it.uri },
                thumbnail = submission.submission.thumbnail,
                currentImageIndex = currentImageIndex,
                onImageChange = { onImageChange(it) },
                onClick = onImageClick
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

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = submission.submission.description,
                textAlign = TextAlign.Justify
            )
        }
        Rating(submission.submission.rating)

        ArtistSection(
            artist = submission.artist,
            onArtistCardClick = { onArtistCardClick(submission.artist?.artistId ?: -1) }
        )

        CharactersSection(
            characters = submission.characters,
            onCharacterCardClick = { onCharacterCardClick(it) }
        )

        TagsSection(
            tags = submission.tags,
            onTagClick = { onTagCardClick(it) }
        )

        HorizontalDivider(
            thickness = 3.dp,
            modifier = Modifier.fillMaxWidth()
        )

        if (submission.images.isNotEmpty()) {
            ImageInfo(submission.images[currentImageIndex])
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ButtonWithIcon(
                iconResource = IconResource.fromDrawableResource(R.drawable.share),
                onClick = {
                    shareImage(
                        context = context,
                        imageUri = submission.images[currentImageIndex].uri
                    )
                },
                text = "Share",
                color = MaterialTheme.colorScheme.secondary
            )
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
fun CharactersSection(
    characters: List<Character>,
    onCharacterCardClick: (Long) -> Unit
) {
    if (characters.isEmpty()) return
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            Icon(
                IconResource.fromDrawableResource(R.drawable.paw_filled).asPainterResource(),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = "Characters",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        characters.forEach { character ->
            SmallCard(
                title = character.name,
                imagePath = character.imagePath,
                onClick = {
                    onCharacterCardClick(character.characterId)
                }
            )
        }
    }
}

@Composable
fun ArtistSection(
    artist: Artist?,
    onArtistCardClick: (Long) -> Unit
) {
    if (artist == null) return
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            Icon(
                IconResource.fromDrawableResource(R.drawable.palette_filled).asPainterResource(),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )

            Text(
                text = "Artist",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        SmallCard(
            title = artist.name,
            imagePath = artist.imagePath,
            onClick = {
                onArtistCardClick(artist.artistId)
            }
        )
    }
}

@Composable
fun TagsSection(
    tags: List<Tag>,
    onTagClick: (Long) -> Unit
) {
    Log.d("TagsSection", "Tags: $tags")

    if (tags.isEmpty()) return
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            Icon(
                IconResource.fromDrawableResource(R.drawable.tag_filled).asPainterResource(),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = "Tags",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        tags.forEach { tag ->

            Card(
                onClick = { onTagClick(tag.tagId) }
            ) {
                Text(
                    text = tag.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ImageInfo(image: Image) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                modifier = Modifier.size(30.dp)
            )
            Text(
                "File Info",
                style = MaterialTheme.typography.headlineSmall,
            )
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

