package dev.tekofx.artganizer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.navigation.Navigation
import dev.tekofx.artganizer.navigation.showBottomAppBar
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.BottomNavigationBar
import dev.tekofx.artganizer.ui.components.input.ArtistListSelect
import dev.tekofx.artganizer.ui.theme.AppTheme
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistDetails
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

@Composable
inline fun <reified T : ViewModel> getActivityViewModel(): T =
    koinViewModel(
        viewModelStoreOwner = LocalActivity.current as ComponentActivity,
        key = T::class.java.name
    )

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            androidContext(this@MainActivity)
            modules(appModules)
        }

        setContent {
            val sharedText = intent?.getStringExtra(Intent.EXTRA_TEXT)
            val navController = rememberNavController()

            AppTheme {
                KoinAndroidContext {
                    MainApp(
                        navController,
                        sharedText
                    )
                }
            }
        }
    }
}

@Composable
fun MainApp(

    navController: NavHostController,
    sharedText: String?,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(), bottomBar = {
            AnimatedVisibility(
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                visible = showBottomAppBar(currentRoute)
            ) {
                BottomNavigationBar(
                    navHostController = navController,
                )
            }
        }) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            Navigation(
                navController,
                sharedText
            )
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
                                navHostController.navigate(NavigateDestinations.ARTISTS_SCREEN)
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

