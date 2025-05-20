package dev.tekofx.artganizer.entities

import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "submissionId"])
data class CharacterSubmissionCrossRef(
    val characterId: Long,
    val submissionId: Long
)