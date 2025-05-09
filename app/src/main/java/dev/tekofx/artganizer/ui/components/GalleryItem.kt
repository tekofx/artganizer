package dev.tekofx.artganizer.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.entities.Submission

@Composable
fun GalleryItem(submission: Submission) {
    Log.d("GalleryItem", "Submission: $submission")

    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(submission.imagePath)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.Fit,

            )
        Text(submission.title)
        Text(submission.description)
    }
}