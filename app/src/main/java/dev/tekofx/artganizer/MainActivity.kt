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
import dev.tekofx.artganizer.navigation.Navigation
import dev.tekofx.artganizer.repository.GalleryRepository
import dev.tekofx.artganizer.ui.BottomNavigationBar
import dev.tekofx.artganizer.ui.theme.AppTheme
import dev.tekofx.artganizer.ui.viewmodels.gallery.GalleryViewModel
import dev.tekofx.artganizer.ui.viewmodels.gallery.GalleryViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val galleryViewModel = ViewModelProvider(
            this, GalleryViewModelFactory(
                GalleryRepository()
            )
        )[GalleryViewModel::class.java]



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
                        Navigation(navController)
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