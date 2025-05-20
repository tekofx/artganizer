package dev.tekofx.artganizer.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["characterId", "submissionId"],
    indices = [Index(value = ["submissionId"])]
)
data class CharacterSubmissionCrossRef(
    val characterId: Long,
    val submissionId: Long
)