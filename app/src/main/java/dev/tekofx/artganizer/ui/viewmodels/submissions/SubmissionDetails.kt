package dev.tekofx.artganizer.ui.viewmodels.submissions

import android.net.Uri
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.utils.dateToString
import dev.tekofx.artganizer.utils.stringToDate
import java.util.Date

data class SubmissionDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val imagesPath: List<Uri> = emptyList<Uri>(),
    val rating: Int = 0,
    val date: String = dateToString(Date()),
    val size: Long = 0L,
    val dimensions: String = "",
    val extension: String = "",
    val palette: List<Int> = emptyList(),
    val artistId: Int? = null,
    val artist: Artist? = null
)

fun SubmissionDetails.toSubmissionWithArtist(): SubmissionWithArtist = SubmissionWithArtist(
    submission = Submission(
        id = id,
        title = title,
        description = description,
        imagesPath = imagesPath,
        rating = rating,
        date = stringToDate(date) ?: Date(),
        size = size,
        dimensions = dimensions,
        extension = extension,
        palette = palette,
        artistId = artistId
    ),
    artist = artist
)

fun SubmissionWithArtist.toSubmissionDetails(): SubmissionDetails = SubmissionDetails(
    id = submission.id,
    title = submission.title,
    description = submission.description,
    imagesPath = submission.imagesPath,
    rating = submission.rating,
    date = submission.date.toString(),
    size = submission.size,
    dimensions = submission.dimensions,
    extension = submission.extension,
    palette = submission.palette,
    artist = artist
)