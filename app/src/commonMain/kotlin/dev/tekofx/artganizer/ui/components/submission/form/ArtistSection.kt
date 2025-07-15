package dev.tekofx.artganizer.ui.components.submission.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.SmallCard
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails

@Composable
fun ArtistSection(
    areThereArtists: Boolean,
    submissionDetails: SubmissionDetails,
    onAddArtistButton: () -> Unit,
    onValueChange: (SubmissionDetails) -> Unit
) {
    if (areThereArtists) {
        Text("Artist", style = MaterialTheme.typography.headlineSmall)
        if (submissionDetails.artist == null) {
            ButtonWithIcon(
                onClick = onAddArtistButton,
                iconResource = IconResource.fromDrawableResource(R.drawable.palette_filled),
                text = "Add Artist",
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            SmallCard(
                title = submissionDetails.artist.name,
                imagePath = submissionDetails.artist.imagePath,
                onClick = onAddArtistButton,
                deletable = true,
                onClear = {
                    onValueChange(
                        submissionDetails.copy(
                            artistId = null,
                            artist = null
                        )
                    )
                }
            )
        }
    }
}