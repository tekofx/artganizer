package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.calendar_outlined
import artganizer.app.generated.resources.device_sd_card_outlined
import artganizer.app.generated.resources.edit
import artganizer.app.generated.resources.file_info
import artganizer.app.generated.resources.file_outlined
import artganizer.app.generated.resources.maximize_outlined
import artganizer.app.generated.resources.palette_filled
import artganizer.app.generated.resources.paw_filled
import artganizer.app.generated.resources.share
import artganizer.app.generated.resources.tag_filled
import artganizer.app.generated.resources.trash
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.SmallCard
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.submission.FullscreenSubmissionViewer
import dev.tekofx.artganizer.ui.components.submission.PaletteColorList
import dev.tekofx.artganizer.ui.components.submission.Rating
import dev.tekofx.artganizer.ui.components.submission.form.SubmissionsForm
import dev.tekofx.artganizer.ui.components.tags.SmallTagCard
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.toSubmissionWithArtist
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import dev.tekofx.artganizer.utils.dateToString
import dev.tekofx.artganizer.utils.formatFileSize
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SubmissionDetailsScreen(
    submissionId: Long,
    navHostController: NavHostController,
    artistsViewModel: ArtistsViewModel = viewModel<ArtistsViewModel>(),
    charactersViewModel: CharactersViewModel = viewModel<CharactersViewModel>(),
    tagsViewModel: TagsViewModel = viewModel<TagsViewModel>(),
    submissionsViewModel: SubmissionsViewModel = viewModel<SubmissionsViewModel>()
) {
    val scope = rememberCoroutineScope()

    // Data
    val submission = submissionsViewModel.currentSubmissionDetails.toSubmissionWithArtist()
    val currentImageIndex by submissionsViewModel.currentImageIndex.collectAsState()

    // UI states
    val showPopup by submissionsViewModel.showPopup.collectAsState()
    val showEdit by submissionsViewModel.showEditSubmission.collectAsState()
    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPage = 0
    )

    LaunchedEffect(Unit) {
        submissionsViewModel.getSubmissionWithArtist(submissionId)
    }

    // Handle back press
    BackHandler(enabled = pagerState.currentPage == 1) {
        scope.launch {
            pagerState.animateScrollToPage(0)
        }
    }

    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                submissionsViewModel.setShowPopup(true)
                submissionsViewModel.deleteSubmission(
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

            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = true
            ) { page ->
                when (page) {
                    0 -> {
                        if (submission.images.isNotEmpty()) {
                            FullscreenSubmissionViewer(
                                imagePaths = submission.images.map { it.uri },
                                currentImageIndex = currentImageIndex,
                                thumbnail = submission.images[currentImageIndex].uri,
                                onImageChange = { submissionsViewModel.setCurrentImage(it) },
                            )
                        }
                    }

                    1 -> {
                        SubmissionInfo(
                            submission,
                            currentImageIndex = currentImageIndex,
                            onArtistCardClick = { artistId ->
                                navHostController.navigate("${NavigateDestinations.ARTISTS_ROOT}/$artistId")
                            },
                            onCharacterCardClick = { characterId ->
                                navHostController.navigate("${NavigateDestinations.CHARACTERS_ROOT}/$characterId")
                            },
                            onTagCardClick = { tagId ->
                                navHostController.navigate("${NavigateDestinations.TAGS_ROOT}/$tagId")
                            },
                            onDelete = {
                                submissionsViewModel.setShowPopup(true)
                            },
                            onEdit = {
                                submissionsViewModel.setShowEditSubmission(true)
                            },
                            onShare = { submissionsViewModel.shareSubmission(submission) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SubmissionInfo(
    submission: SubmissionWithArtist,
    currentImageIndex: Int,
    onArtistCardClick: (Long) -> Unit,
    onCharacterCardClick: (Long) -> Unit,
    onTagCardClick: (Long) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onShare: (SubmissionWithArtist) -> Unit
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

        SubmissionDetails(
            submission,
            currentImageIndex = currentImageIndex,
            onArtistCardClick = onArtistCardClick,
            onCharacterCardClick = onCharacterCardClick,
            onTagCardClick = onTagCardClick,
            onEdit = onEdit,
            onDelete = onDelete,
            onShare = onShare
        )
    }
}

@Composable
fun SubmissionDetails(
    submission: SubmissionWithArtist,
    currentImageIndex: Int,
    onArtistCardClick: (Long) -> Unit,
    onCharacterCardClick: (Long) -> Unit,
    onTagCardClick: (Long) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onShare: (SubmissionWithArtist) -> Unit
) {

    Column {

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
                icon = Res.drawable.share,
                onClick = {
                    onShare(submission)

                },
                text = "Share",
                color = MaterialTheme.colorScheme.secondary
            )
            ButtonWithIcon(
                icon = Res.drawable.edit,
                onClick = { onEdit() },
                text = "Edit"
            )
            ButtonWithIcon(
                icon = Res.drawable.trash,
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
                painterResource(Res.drawable.paw_filled),
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
                painterResource(Res.drawable.palette_filled),
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
                painterResource(Res.drawable.tag_filled),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = "Tags",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tags) {
                SmallTagCard(
                    tag = it,
                    onClick = { onTagClick(it.tagId) },
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
                painterResource(Res.drawable.file_info),
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
                    painterResource(Res.drawable.calendar_outlined),
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
                    painterResource(Res.drawable.maximize_outlined),
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
                    painterResource(Res.drawable.device_sd_card_outlined),
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
                    painterResource(Res.drawable.file_outlined),
                    contentDescription = ""
                )
            }
        }
    }
}

