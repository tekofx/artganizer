package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EmptyAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
) {
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
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
        val initials = (name.take(1)).uppercase()
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(color))
        }
        Text(
            text = initials,
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = size.value.sp / 2),
            color = Color.White
        )
    }
}