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
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.user_filled
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun Avatar(
    imagePath: String?,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    size: Dp? = null
) {

    val finalModifier = if (size == null) {
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
            modifier = finalModifier
        )
    } else {
        EmptyAvatar(
            icon = Res.drawable.user_filled,
            size = size,
            shape = shape
        )
    }
}