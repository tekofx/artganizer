package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyAvatar(
    modifier: Modifier = Modifier,
    size: Dp?,
    icon: DrawableResource,
    shape: Shape = RoundedCornerShape(50.dp)
) {

    var boxWidth by remember { mutableIntStateOf(0) }

    val newSize = size ?: boxWidth.dp

    val finalModifier = if (size == null) {
        modifier
            .clip(shape)
            .fillMaxWidth()
            .aspectRatio(1f)
    } else {
        modifier
            .clip(shape)
            .size(newSize)
            .aspectRatio(1f)
    }
    val color = remember {
        Color.Gray
    }
    Box(
        finalModifier
            .background(color)
            .onSizeChanged { size: IntSize ->
                boxWidth = size.width
            }, contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(newSize - newSize / 3),
            painter = painterResource(icon),
            contentDescription = "icon",
        )
    }
}