package dev.tekofx.artganizer.ui.components.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.components.SubmissionCount
import dev.tekofx.artganizer.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ArtistCard(
    artist: ArtistWithSubmissions,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                artist.artist.imagePath,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                size = 100.dp
            )
            Column(
                modifier = Modifier
                    .weight(3f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = artist.artist.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displayMedium
                )
                SubmissionCount(artist.submissions.size)
            }
        }
    }
}

@Composable
@Preview
fun ArtistCardPreview() {
    val artist = ArtistWithSubmissions(
        artist = Artist(

            artistId = 1,
            name = "John Doe",
            imagePath = null,
            socialNetworks = emptyList()
        ),
        submissions = emptyList()
    )
    AppTheme {
        ArtistCard(artist, onClick = {})
    }
}