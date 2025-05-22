package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource

@Composable
fun Rating(
    rating: Int,
    onRatingChange: (Int) -> Unit = {},
) {
    if (rating == 0) return
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Rating",
            style = MaterialTheme.typography.headlineSmall,
        )
        Row {
            for (i in 1..5) {
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
}