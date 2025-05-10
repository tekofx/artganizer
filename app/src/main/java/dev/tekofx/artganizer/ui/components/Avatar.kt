package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Avatar(
    imagePath: String?,
    fallbackText: String,
    modifier: Modifier = Modifier
) {
    if (imagePath != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .aspectRatio(1f)
        )
    } else {
        EmptyAvatar(fallbackText, fallbackText, size = 50.dp)
    }
}