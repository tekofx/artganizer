package dev.tekofx.artganizer.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["tagId", "submissionId"],
    indices = [Index(value = ["submissionId"])]
)
data class TagSubmissionCrossRef(
    val tagId: Long,
    val submissionId: Long
)