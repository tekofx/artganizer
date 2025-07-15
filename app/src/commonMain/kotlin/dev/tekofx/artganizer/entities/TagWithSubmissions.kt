package dev.tekofx.artganizer.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TagWithSubmissions(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "submissionId",
        associateBy = Junction(TagSubmissionCrossRef::class)
    )
    val submissions: List<Submission>
)