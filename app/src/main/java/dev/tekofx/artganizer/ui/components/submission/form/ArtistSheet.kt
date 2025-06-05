package dev.tekofx.artganizer.ui.components.submission.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ArtistListSelect
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ArtistSheet(
    showArtistsSheet: Boolean,
    artists: List<ArtistWithSubmissions>,
    onSearch: (String) -> Unit,
    onItemValueChange: (Artist) -> Unit,
    closeBottomSheet: () -> Unit,
    queryText: String
) {
    if (showArtistsSheet) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Select Artist", style = MaterialTheme.typography.headlineSmall)
            ArtistListSelect(
                items = artists,
                onQueryChange = { onSearch(it) },
                onArtistClick = { artist ->
                    onItemValueChange(artist)
                    closeBottomSheet()
                },
                query = queryText,
            )
            ButtonWithIcon(
                onClick = {
                    closeBottomSheet()
                },
                iconResource = IconResource.fromDrawableResource(R.drawable.x),
                text = "Close",
            )

        }
    }
}