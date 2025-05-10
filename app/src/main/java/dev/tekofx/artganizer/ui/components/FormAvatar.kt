package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.R

@Composable
fun FormAvatar(
    imagePath: String?,
    size: Dp = 100.dp,
    onImageSelect: () -> Unit
) {
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Avatar Circle
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(size)
        ) {
            if (imagePath != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagePath)
                        .build(),
                    contentDescription = "icon",
                    contentScale = ContentScale.Inside,
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.paw_filled),
                    contentDescription = "Placeholder",
                    tint = Color.White,
                    modifier = Modifier.size(size / 2)
                )
            }
        }

        // Icon Overlay
        Icon(
            painter = painterResource(id = R.drawable.tag_filled),
            contentDescription = "Select Image",
            tint = Color.White,
            modifier = Modifier
                .size(size / 4)
                .clickable { onImageSelect() }
        )
    }
}