package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
expect fun ArtistDetailsScreen(
    artistId: Long,
    navHostController: NavHostController,
)
