package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource

@Composable
fun RatingSelector(
    rating: Int, onRatingChange: (Int) -> Unit, maxRating: Int = 5
) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        for (i in 1..maxRating) {
            Icon(
                if (i <= rating) IconResource.fromDrawableResource(R.drawable.star_filled)
                    .asPainterResource()
                else IconResource.fromDrawableResource(R.drawable.star_outlined)
                    .asPainterResource(),
                contentDescription = "Rating $i",
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onRatingChange(i) },
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}