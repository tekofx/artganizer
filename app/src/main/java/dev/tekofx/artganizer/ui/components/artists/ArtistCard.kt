package dev.tekofx.artganizer.ui.components.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.theme.AppTheme

@Composable
fun ArtistCard(artist: Artist, navHostController: NavHostController) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(150.dp),
        color = Color.LightGray,
        shape = MaterialTheme.shapes.medium,
        onClick = { navHostController.navigate("${NavigateDestinations.ARTISTS_SCREEN}/${artist.id}") }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                artist.imagePath,
                artist.name,
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
                    text = artist.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "Artist description or details",
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
@Preview
fun ArtistCardPreview() {
    val artist = Artist(
        id = 1,
        name = "John Doe",
        imagePath = null,
        socialNetworks = emptyList()
    )
    val navHostController = NavHostController(LocalContext.current)
    AppTheme {
        ArtistCard(artist, navHostController)
    }
}