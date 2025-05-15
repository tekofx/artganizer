package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.utils.AVATAR_SIZE

@Composable
fun FormAvatar(
    fallbackText: String,
    imagePath: String?,
    onImageSelect: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        // Avatar Circle
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
        ) {
            Avatar(
                imagePath = imagePath,
                fallbackText = fallbackText,
                size = AVATAR_SIZE
            )
        }

        // Icon Overlay
        Icon(
            painter = painterResource(id = R.drawable.edit),
            contentDescription = "Select Image",
            tint = Color.White,
            modifier = Modifier.clickable { onImageSelect() }
        )
    }
}