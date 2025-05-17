package dev.tekofx.artganizer.ui.viewmodels.artists

import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.entities.Submission

data class ArtistDetails(
    val id: Int = 0,
    val name: String = "",
    val imagePath: String? = null,
    val socialNetworks: List<String> = emptyList(),
    val submissions: List<Submission> = emptyList()
)

fun ArtistDetails.toArtistWithSubmissions(): ArtistWithSubmissions = ArtistWithSubmissions(
    Artist(
        id = id,
        name = name,
        imagePath = imagePath,
        socialNetworks = socialNetworks
    ),
    submissions = submissions
)

fun ArtistWithSubmissions.toArtistDetails(): ArtistDetails = ArtistDetails(
    id = artist.id,
    name = artist.name,
    imagePath = artist.imagePath,
    socialNetworks = artist.socialNetworks,
    submissions = submissions
)