package dev.tekofx.artganizer

import androidx.compose.runtime.Composable
import dev.tekofx.artganizer.navigation.Navigation
import dev.tekofx.artganizer.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules()
        }
    ) {
        AppTheme() {
            /*var showContent by remember { mutableStateOf(false) }
            val tags by tagsViewModel.tags.collectAsState()
            val artists by artistViewModel.artists.collectAsState()
            ArtistCreationScreen()*/
            Navigation()
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
