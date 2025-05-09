package dev.tekofx.artganizer.ui.components.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Artist

@Composable
fun ArtistCard(artist: Artist) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        color = Color.LightGray,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.paw_filled), // Replace with your icon resource
                contentDescription = "Artist Avatar",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Column {
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