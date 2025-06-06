package dev.tekofx.artganizer.ui.components.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.TagWithSubmissions
import dev.tekofx.artganizer.ui.IconResource

@Composable
fun TagCard(
    tag: TagWithSubmissions,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.clickable(
            onClick = onClick,
            indication = null,
            interactionSource = null,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    IconResource.fromDrawableResource(R.drawable.tag_filled).asPainterResource(),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = tag.tag.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = tag.submissions.size.toString(),
            )
        }
    }
}