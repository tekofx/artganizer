package dev.tekofx.artganizer.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SubmissionWithArtist(
    @Embedded val submission: Submission,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "artistId"
    )
    val artist: Artist?,
    @Relation(
        parentColumn = "submissionId",
        entityColumn = "submissionId"
    )
    val images: List<Image>,

    @Relation(
        parentColumn = "submissionId",
        entityColumn = "characterId",
        associateBy = Junction(CharacterSubmissionCrossRef::class)
    )
    val characters: List<Character>,

    @Relation(
        parentColumn = "submissionId",
        entityColumn = "tagId",
        associateBy = Junction(TagSubmissionCrossRef::class)
    )
    val tags: List<Tag>
)