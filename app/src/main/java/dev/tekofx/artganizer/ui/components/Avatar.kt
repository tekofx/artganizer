package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Avatar(
    imagePath: String?,
    fallbackText: String,
) {
    if (imagePath != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .height(50.dp)
        )
    } else {
        EmptyAvatar(fallbackText, fallbackText, size = 50.dp)
    }
}