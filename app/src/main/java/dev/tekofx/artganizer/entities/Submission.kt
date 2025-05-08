package dev.tekofx.artganizer.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "submissions")
data class Submission(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val imagePath: String,
)