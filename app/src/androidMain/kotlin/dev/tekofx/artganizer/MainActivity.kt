package dev.tekofx.artganizer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ArtistListSelect
import dev.tekofx.artganizer.ui.theme.AppTheme
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistDetails
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sharedText = intent?.getStringExtra(Intent.EXTRA_TEXT)
            AppTheme {
                App(
                    sharedText
                )
            }
        }
    }
}


@Composable
fun HandleSharedLink(
    sharedText: String,
    artistsViewModel: ArtistsViewModel,
    navHostController: NavHostController
) {
    val artists by artistsViewModel.artists.collectAsState()
    var selectedArtist: Artist? by remember { mutableStateOf(null) }
    val queryText by artistsViewModel.queryText.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 10.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            if (selectedArtist == null) {
                Text(text = "Importing Link: $sharedText")
                ArtistListSelect(
                    artists = artists,
                    onArtistClick = { artist ->
                        val newSocialNetworks =
                            artist.socialNetworks.toMutableList()
                        newSocialNetworks.add(sharedText)
                        artistsViewModel.updateCurrentUiState(
                            ArtistDetails(
                                id = artist.artistId,
                                name = artist.name,
                                imagePath = artist.imagePath,
                                socialNetworks = newSocialNetworks
                            )
                        )
                        scope.launch {
                            artistsViewModel.editArtist(context)
                        }
                        selectedArtist = artist
                    },
                    textFieldState = artistsViewModel.textFieldState
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(100.dp),
                            tint = Color(0xFF4CAF50),
                            painter = IconResource.fromDrawableResource(R.drawable.check)
                                .asPainterResource(),
                            contentDescription = ""
                        )

                        Text(
                            "Link imported to ${selectedArtist?.name}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Button(
                            onClick = {
                                navHostController.navigate(NavigateDestinations.ARTISTS_ROOT)
                            },
                        ) {
                            Text("Artists Page")
                        }
                    }
                }
            }
        }
    }
}

