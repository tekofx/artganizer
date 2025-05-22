package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp


@Composable
fun EmptyAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp?,
    shape: Shape = RoundedCornerShape(50.dp)
) {

    var boxWidth by remember { mutableIntStateOf(0) }

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
    val color = remember {
        Color(
            android.graphics.Color.HSVToColor(
                floatArrayOf(
                    (0..360).random().toFloat(), // Random hue
                    0.5f, // Saturation
                    0.4f  // Lightness
                )
            )
        )
    }
    Box(
        finalModifier
            .background(color)
            .onSizeChanged { size: IntSize ->
                boxWidth = size.width
            }, contentAlignment = Alignment.Center
    ) {
        val initials = if (name.isNotEmpty()) name.take(1).uppercase() else "A"
        Text(
            text = initials,
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = boxWidth.sp / 3),
            color = Color.White
        )
    }
}