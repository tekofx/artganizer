package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import dev.tekofx.artganizer.entities.Submission

@Composable
fun Gallery(
    submissions: List<Submission>,
    onImageClick: (Long) -> Unit = {}
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(submissions) { submission ->
            GalleryItem(
                submission = submission,
                onImageClick = onImageClick,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryItem(
    submission: Submission,
    onImageClick: (Long) -> Unit,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(submission.thumbnail)
            .build(),
        contentDescription = submission.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onImageClick(submission.submissionId)
            }
    )
}