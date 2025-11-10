package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.edit
import artganizer.composeapp.generated.resources.trash
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.ui.components.artists.SocialNetworks
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.utils.AVATAR_SIZE

@Composable
fun ArtistInfo(
    artistWithSubmissions: ArtistWithSubmissions,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            Avatar(
                artistWithSubmissions.artist.imagePath,
                size = AVATAR_SIZE.dp
            )
        }
        item {
            Text(
                text = artistWithSubmissions.artist.name,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
            )
        }
        item {
            SocialNetworks(artistWithSubmissions.artist.socialNetworks)
        }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ButtonWithIcon(
                    onClick = onEditClick,
                    text = "Edit",
                    drawableResource = Res.drawable.edit,
                )
                ButtonWithIcon(
                    onClick = onDeleteClick,
                    text = "Delete",
                    drawableResource = Res.drawable.trash,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}