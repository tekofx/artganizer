package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.ui.components.SmallCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistListSelect(
    items: List<ArtistWithSubmissions>,
    onQueryChange: (String) -> Unit,
    onArtistClick: (Artist) -> Unit,
    query: String,
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp) // Reserve space for the SearchBar
        ) {
            if (items.isEmpty()) {
                Text(
                    text = "No artists with current search",
                    style = MaterialTheme.typography.bodyMedium,
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items.size) { index ->
                        val item = items[index]
                        if (item.artist.name.contains(query, ignoreCase = true)) {
                            SmallCard(
                                title = item.artist.name,
                                imagePath = item.artist.imagePath,
                                onClick = {
                                    onArtistClick(item.artist)
                                },
                            )
                        }
                    }
                }
            }
        }

        // SearchBar at the bottom
        SearchBar(
            queryText = query,
            onValueChange = { onQueryChange(it) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}