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
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.ui.components.BottomNavigationBar
import dev.tekofx.artganizer.ui.theme.AppTheme
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModelFactory
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val submissionRepository by lazy {
            SubmissionRepository(AppDatabase.getDatabase(this).submissionDao())
        }

        val artistsRepository by lazy {
            ArtistsRepository(AppDatabase.getDatabase(this).artistDao())
        }


        val submissionsViewModel = ViewModelProvider(
            this, SubmissionsViewModelFactory(
                submissionRepository
            )
        )[SubmissionsViewModel::class.java]

        val artistsViewModel = ViewModelProvider(
            this, ArtistsViewModelFactory(
                artistsRepository
            )
        )[ArtistsViewModel::class.java]

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
                        Navigation(navController, submissionsViewModel, artistsViewModel)
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