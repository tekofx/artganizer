package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsUiState

@Composable
fun InteractiveGallery(
    submissions: SubmissionsUiState,
    isSelecting: Boolean,
    onImageClick: (Long) -> Unit,
    onSelectImage: (Long) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(
            items = submissions.submissions,
            key = { it.submission.submissionId }
        ) { submission ->
            InteractiveGalleryItem(
                submission = submission,
                selected = submissions.selectedSubmissions.contains(submission.submission.submissionId),
                isSelecting = isSelecting,
                onImageClick = {
                    if (submissions.selectedSubmissions.isEmpty()) {
                        onImageClick(submission.submission.submissionId)
                    } else {
                        onSelectImage(submission.submission.submissionId)
                    }
                },
                onLongClick = {
                    onSelectImage(submission.submission.submissionId)
                },
                modifier = Modifier.animateItem() // Add animation for item placement
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveGalleryItem(
    submission: SubmissionWithArtist,
    selected: Boolean,
    isSelecting: Boolean,
    onImageClick: (Long) -> Unit,
    onLongClick: (Long) -> Unit,
    modifier: Modifier = Modifier // Accept modifier for animation
) {
    Box(
        modifier = modifier
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(submission.submission.thumbnail)
                .build(),
            contentDescription = submission.submission.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.small)
                .combinedClickable(
                    onClick = { onImageClick(submission.submission.submissionId) },
                    onLongClick = { onLongClick(submission.submission.submissionId) }
                )
        )

        if (selected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .zIndex(1f)
                    .background(Color.White.copy(alpha = 0.5f)) // adjust alpha for transparency
            )
        }

        if (isSelecting) {
            Icon(
                painter = if (selected) painterResource(R.drawable.circle_check_filled)
                else painterResource(R.drawable.circle),
                contentDescription = if (selected) "Selected" else "Not selected",
                tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(30.dp)
                    .zIndex(2f)
                    .padding(top = 10.dp, end = 10.dp)
            )
        }
    }
}