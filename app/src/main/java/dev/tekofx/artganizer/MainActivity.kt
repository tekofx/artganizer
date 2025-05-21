package dev.tekofx.artganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tekofx.artganizer.database.AppDatabase
import dev.tekofx.artganizer.navigation.Navigation
import dev.tekofx.artganizer.navigation.showBottomAppBar
import dev.tekofx.artganizer.repository.ArtistsRepository
import dev.tekofx.artganizer.repository.CharactersRepository
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.ui.components.BottomNavigationBar
import dev.tekofx.artganizer.ui.theme.AppTheme
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModelFactory
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModelFactory
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModelFactory

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
                submissionRepository,
                imageRepository
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
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            AppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    bottomBar = {
                        AnimatedVisibility(
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it }),
                            visible = showBottomAppBar(currentRoute)
                        ) {
                            BottomNavigationBar(
                                navHostController = navController,
                            )
                        }
                    }
                ) { padding ->
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
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}