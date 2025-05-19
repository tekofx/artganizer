package dev.tekofx.artganizer.ui.viewmodels.submissions

import android.net.Uri
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist

data class SubmissionDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val imagePath: Uri = Uri.EMPTY,
    val rating: Int = 0,
    val artistId: Int? = null,
    val artist: Artist? = null,
    val images: List<Image> = emptyList<Image>()
)

fun SubmissionDetails.toSubmissionWithArtist(): SubmissionWithArtist = SubmissionWithArtist(
    submission = Submission(
        id = id,
        title = title,
        description = description,
        imagePath = imagePath,
        rating = rating,
        artistId = artistId
    ),
    artist = artist,
    images = images
)

fun SubmissionWithArtist.toSubmissionDetails(): SubmissionDetails = SubmissionDetails(
    id = submission.id,
    title = submission.title,
    description = submission.description,
    imagePath = submission.imagePath,
    rating = submission.rating,
    artist = artist,
    images = images
)