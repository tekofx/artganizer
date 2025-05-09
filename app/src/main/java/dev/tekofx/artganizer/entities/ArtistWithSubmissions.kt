package dev.tekofx.artganizer.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ArtistWithSubmissions(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "id",
        entityColumn = "artistId"
    )
    val submissions: List<Submission>
)