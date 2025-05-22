package dev.tekofx.artganizer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tekofx.artganizer.database.AppDatabase
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.navigation.Navigation
import dev.tekofx.artganizer.navigation.showBottomAppBar
import dev.tekofx.artganizer.repository.ArtistsRepository
import dev.tekofx.artganizer.repository.CharactersRepository
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.ui.components.BottomNavigationBar
import dev.tekofx.artganizer.ui.components.input.ArtistListSelect
import dev.tekofx.artganizer.ui.theme.AppTheme
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistDetails
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModelFactory
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModelFactory
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val submissionRepository by lazy {
            SubmissionRepository(
                AppDatabase.getDatabase(this).submissionDao(),
                AppDatabase.getDatabase(this).characterSubmissionCrossRefDao()
            )
        }

        val artistsRepository by lazy {
            ArtistsRepository(AppDatabase.getDatabase(this).artistDao())
        }

        val imageRepository by lazy {
            ImageRepository(AppDatabase.getDatabase(this).imageDao())
        }

        val charactersRepository by lazy {
            CharactersRepository(AppDatabase.getDatabase(this).characterDao())
        }


        val submissionsViewModel = ViewModelProvider(
            this, SubmissionsViewModelFactory(
                submissionRepository, imageRepository
            )
        )[SubmissionsViewModel::class.java]

        val artistsViewModel = ViewModelProvider(
            this, ArtistsViewModelFactory(
                artistsRepository
            )
        )[ArtistsViewModel::class.java]

        val charactersViewModel = ViewModelProvider(
            this, CharactersViewModelFactory(
                charactersRepository
            )
        )[CharactersViewModel::class.java]

        setContent {
            val sharedText = intent?.getStringExtra(Intent.EXTRA_TEXT)
            AppTheme {
                if (sharedText != null) {
                    // Pass the shared link to your Composable or ViewModel
                    HandleSharedLink(sharedText, artistsViewModel)
                } else {
                    MainApp(
                        submissionsViewModel,
                        artistsViewModel,
                        charactersViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MainApp(
    submissionsViewModel: SubmissionsViewModel,
    artistsViewModel: ArtistsViewModel,
    charactersViewModel: CharactersViewModel
) {
    val navController = rememberNavController()
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
                submissionsViewModel,
                artistsViewModel,
                charactersViewModel
            )
        }
    }
}

@Composable
fun HandleSharedLink(
    sharedText: String,
    artistsViewModel: ArtistsViewModel
) {
    val artists by artistsViewModel.artists.collectAsState()
    var selectedArtist: Artist? by remember { mutableStateOf(null) }
    val queryText by artistsViewModel.queryText.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(text = "Importing Link: $sharedText")
            ArtistListSelect(
                items = artists,
                onQueryChange = {
                    artistsViewModel.onSearchTextChanged(it)
                },
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
                query = queryText,
            )
        }
    }
}

