package dev.tekofx.artganizer.ui.screens.artists

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.ArtistDetailsRoute
import dev.tekofx.artganizer.ui.components.artists.ArtistComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistDetailsScreen(artist: ArtistDetailsRoute, navController: NavHostController) {
    ArtistComponent(artist, navController = navController)
}