package dev.tekofx.artganizer.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "submissions",
    foreignKeys = [
        ForeignKey(
            entity = Artist::class,
            parentColumns = ["id"],
            childColumns = ["artistId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["artistId"])]
)
data class Submission(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val imagePath: String,
    val rating: Int = 0,
    val date: Date = Date(),
    val sizeInMb: Double = 0.0,
    val dimensions: String = "",
    val extension: String = "",
    val artistId: Int
)