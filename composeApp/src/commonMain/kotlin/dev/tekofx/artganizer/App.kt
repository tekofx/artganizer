package dev.tekofx.artganizer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.compose_multiplatform
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import kotlinx.coroutines.runBlocking
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dev.tekofx.artganizer.ui.screens.artists.ArtistCreationScreen
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel

@Composable
@Preview
fun App() {
    val tagsViewModel = koinViewModel<TagsViewModel>()
    val artistViewModel = koinViewModel<ArtistsViewModel>()
    KoinApplication(
        application = {
            modules()
        }
    ) {
        MaterialTheme {
            var showContent by remember { mutableStateOf(false) }
            val tags by tagsViewModel.tags.collectAsState()
            val artists by artistViewModel.artists.collectAsState()
            ArtistCreationScreen()
            /*Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                Text(artistViewModel.newArtistUiState.artistDetails.name)
                Text( "Artists in DB: ${artists.size}" )


                TextField(
                    value = artistViewModel.newArtistUiState.artistDetails.name,
                    onValueChange = {
                        artistViewModel.updateNewUiState(artistViewModel.newArtistUiState.artistDetails.copy(name = it))
                    }
                )
                Button(onClick = {
                    runBlocking {
                        artistViewModel.saveArtist()
                    }
                }) {
                    Text("Save")
                }

                tags.forEach {
                    Text("Tag: ${it.tag.name}")
                }

                artists.forEach {
                    Text("Artist: ${it.artist.name}")
                }

                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }
            }*/
        }
    }
}
