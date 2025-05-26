package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.entities.Submission

@Composable
fun InteractiveGallery(
    submissions: List<Submission>,
    onImageClick: (Long) -> Unit = {}
) {
    var selectedIds by remember { mutableStateOf(setOf<Long>()) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(submissions) { submission ->
            val isSelected = selectedIds.contains(submission.submissionId)
            InteractiveGalleryItem(
                submission = submission,
                selected = isSelected,
                onImageClick = onImageClick,
                onLongClick = {
                    selectedIds = if (isSelected) {
                        selectedIds - submission.submissionId
                    } else {
                        selectedIds + submission.submissionId
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveGalleryItem(
    submission: Submission,
    selected: Boolean,
    onImageClick: (Long) -> Unit,
    onLongClick: () -> Unit
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
            .then(
                if (selected) Modifier.border(
                    BorderStroke(2.dp, Color.Blue),
                    MaterialTheme.shapes.small
                ) else Modifier
            )
            .combinedClickable(
                onClick = { onImageClick(submission.submissionId) },
                onLongClick = onLongClick
            )
    )
}