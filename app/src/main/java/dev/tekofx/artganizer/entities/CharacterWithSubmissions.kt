package dev.tekofx.artganizer.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CharacterWithSubmissions(
    @Embedded val character: Character,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "submissionId",
        associateBy = Junction(CharacterSubmissionCrossRef::class)
    )
    val submissions: List<Submission>
)