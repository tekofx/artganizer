package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PaletteColorList(palette: List<Int>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(palette.size) { index ->
            Box(
                modifier = Modifier
                    .fillParentMaxWidth(1f / palette.size)
                    .height(50.dp)
                    .background(Color(palette[index]))
            )
        }
    }
}