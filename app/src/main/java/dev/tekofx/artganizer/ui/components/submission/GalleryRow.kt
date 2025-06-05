package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun GalleryRow(
    submissions: List<Submission>,
    onImageClick: (Long) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        // Ensure the row always has 3 items by adding placeholders for missing submissions
        val paddedSubmissions = submissions + List(3 - submissions.size) { null }
        paddedSubmissions.forEach { submission ->
            if (submission != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(submission.thumbnail)
                        .build(),
                    contentDescription = submission.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.small)
                        .clickable {
                            onImageClick(submission.submissionId)
                        }
                )
            } else {
                // Placeholder for missing submissions
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.small)
                ) {}
            }
        }
    }
}