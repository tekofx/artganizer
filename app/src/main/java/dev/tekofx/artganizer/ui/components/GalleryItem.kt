package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter
import dev.tekofx.artganizer.entities.Submission

@Composable
fun GalleryItem(submission: Submission) {
    Column {
        Image(
            painter = rememberAsyncImagePainter(model = submission.imagePath),
            contentDescription = "Image from private storage"
        )
        Text(submission.title)
        Text(submission.description)
    }
}