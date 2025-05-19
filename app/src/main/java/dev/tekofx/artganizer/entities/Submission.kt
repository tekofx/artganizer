package dev.tekofx.artganizer.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "submissions",
    foreignKeys = [
        ForeignKey(
            entity = Artist::class,
            parentColumns = ["id"],
            childColumns = ["artistId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [androidx.room.Index(value = ["artistId"])]
)
data class Submission(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val rating: Int = 0,
    val imagePath: Uri = Uri.EMPTY,
    // Foreign keys
    val artistId: Int? = null
)