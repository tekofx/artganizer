package dev.tekofx.artganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import dev.tekofx.artganizer.database.SubmissionDatabase
import dev.tekofx.artganizer.navigation.Navigation
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.ui.BottomNavigationBar
import dev.tekofx.artganizer.ui.theme.AppTheme
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val submissionRepository by lazy {
            SubmissionRepository(SubmissionDatabase.getDatabase(this).submissionDao())
        }


        val galleryViewModel = ViewModelProvider(
            this, SubmissionsViewModelFactory(
                submissionRepository
            )
        )[SubmissionsViewModel::class.java]



        setContent {
            val navController = rememberNavController()
            AppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    bottomBar = {
                        BottomNavigationBar(
                            navHostController = navController,
                        )
                    }
                ) { padding ->
                    Box(
                        modifier = Modifier.padding(padding)
                    ) {
                        Navigation(navController, galleryViewModel)
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