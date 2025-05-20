package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Avatar(
    imagePath: String?,
    fallbackText: String,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    size: Dp? = null
) {

    val finalModifer = if (size == null) {
        modifier
            .clip(shape)
            .fillMaxWidth()
            .aspectRatio(1f)
    } else {
        modifier
            .clip(shape)
            .size(size)
            .aspectRatio(1f)
    }

    if (imagePath != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.Crop,
            modifier = finalModifer
        )
    } else {
        EmptyAvatar(fallbackText, size = size, shape = shape)
    }
}