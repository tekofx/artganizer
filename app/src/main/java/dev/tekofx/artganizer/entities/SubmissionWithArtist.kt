package dev.tekofx.artganizer.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SubmissionWithArtist(
    @Embedded val submission: Submission,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "id"
    )
    val artist: Artist?
)