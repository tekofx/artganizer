package dev.tekofx.artganizer.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "submissions",
    foreignKeys = [
        ForeignKey(
            entity = Artist::class,
            parentColumns = ["artistId"],
            childColumns = ["artistId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [androidx.room.Index(value = ["artistId"])]
)
data class Submission(
    @PrimaryKey(autoGenerate = true)
    val submissionId: Long = 0,
    val title: String,
    val description: String,
    val rating: Int = 0,
    val thumbnail: String = "",
    // Foreign keys
    val artistId: Long? = null,
    val characterId: Long? = null

)