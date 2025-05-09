package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource

@Composable
fun Rating(rating: Int) {
    for (i in 1..5) {
        Icon(
            if (i <= rating) IconResource.fromDrawableResource(R.drawable.star_filled)
                .asPainterResource()
            else IconResource.fromDrawableResource(R.drawable.star_outlined)
                .asPainterResource(),
            contentDescription = "Rating $i",
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}