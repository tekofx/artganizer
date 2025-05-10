package dev.tekofx.artganizer.ui.components.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.EmptyAvatar

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
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (artist.imagePath != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artist.imagePath)
                        .build(),
                    contentDescription = "icon",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .height(50.dp)
                )
            } else {
                EmptyAvatar(artist.name, artist.name, size = 50.dp)
            }
            Column(
            ) {
                Text(
                    text = artist.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Artist description or details",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}