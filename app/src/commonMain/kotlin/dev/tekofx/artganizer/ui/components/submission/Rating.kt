package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.star_filled
import artganizer.app.generated.resources.star_outlined
import org.jetbrains.compose.resources.painterResource

@Composable
fun Rating(
    rating: Int,
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
                    if (i <= rating) painterResource(Res.drawable.star_filled)
                    else painterResource(Res.drawable.star_outlined),
                    contentDescription = "Rating $i",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}