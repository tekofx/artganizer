package dev.tekofx.artganizer.ui.screens.artists

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import dev.tekofx.artganizer.navigation.AppRoute
import dev.tekofx.artganizer.ui.components.artists.ArtistComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
actual fun ArtistDetailsScreen(artist: AppRoute.ArtistDetails) {
    ArtistComponent(artist)
}