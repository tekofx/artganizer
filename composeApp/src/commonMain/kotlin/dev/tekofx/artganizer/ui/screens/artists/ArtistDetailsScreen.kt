package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.entities.ArtistWithSubmissions

@Composable
expect fun ArtistDetailsScreen(artist: ArtistWithSubmissions)
