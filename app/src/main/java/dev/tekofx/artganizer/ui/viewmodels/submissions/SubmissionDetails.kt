package dev.tekofx.artganizer.ui.viewmodels.submissions

import android.net.Uri
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist

data class SubmissionDetails(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val imagePath: Uri = Uri.EMPTY,
    val rating: Int = 0,
    val artistId: Long? = null,
    val artist: Artist? = null,
    val images: List<Image> = emptyList<Image>(),
    val characters: List<Character> = emptyList<Character>()
)

fun SubmissionDetails.toSubmissionWithArtist(): SubmissionWithArtist = SubmissionWithArtist(
    submission = Submission(
        submissionId = id,
        title = title,
        description = description,
        imagePath = imagePath,
        rating = rating,
        artistId = artistId
    ),
    artist = artist,
    images = images,
    characters = characters
)

fun SubmissionWithArtist.toSubmissionDetails(): SubmissionDetails = SubmissionDetails(
    id = submission.submissionId,
    title = submission.title,
    description = submission.description,
    imagePath = submission.imagePath,
    rating = submission.rating,
    artist = artist,
    images = images
)