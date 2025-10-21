package dev.tekofx.artganizer.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.share
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LeftPanel() {
    // ViewModels
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val tagsViewModel = koinViewModel<TagsViewModel>()

    // Entities
    val artists by artistsViewModel.artists.collectAsState()

    Column(modifier = Modifier.fillMaxHeight().padding(10.dp)) {
        TopButtons()
        LazyColumn {
            item {
                ArtistSection(artists)
            }
        }
    }
}

@Composable
fun ArtistSection(artists: List<ArtistWithSubmissions>) {
    Column {
        Text("Artists")
        Column {
            artists.forEach {
                Text(it.artist.name)
            }
        }

    }

}


@Composable
fun TopButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painterResource(Res.drawable.share),
            contentDescription = ""
        )
        Icon(
            painterResource(Res.drawable.share),
            contentDescription = ""
        )
        Icon(
            painterResource(Res.drawable.share),
            contentDescription = ""
        )
    }
}
