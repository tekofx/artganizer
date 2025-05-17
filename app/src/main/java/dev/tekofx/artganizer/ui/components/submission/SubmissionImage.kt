package dev.tekofx.artganizer.ui.components.submission

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SubmissionImage(
    title: String,
    imagePath: Uri
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imagePath)
            .build(),
        contentDescription = title,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}