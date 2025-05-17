package dev.tekofx.artganizer.ui.viewmodels.artists

import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.ArtistWithSubmissions


data class ArtistUiState(
    val artistDetails: ArtistDetails = ArtistDetails(),
    val isEntryValid: Boolean = false
)

fun ArtistUiState.toArtistWithSubmissions(): ArtistWithSubmissions = ArtistWithSubmissions(
    Artist(
        id = artistDetails.id,
        name = artistDetails.name,
        imagePath = artistDetails.imagePath,
        socialNetworks = artistDetails.socialNetworks
    ),
    submissions = artistDetails.submissions
)