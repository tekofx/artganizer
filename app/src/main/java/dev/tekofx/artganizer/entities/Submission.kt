package dev.tekofx.artganizer.entities

import android.net.Uri
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
    val imagesPath: List<Uri>,
    val rating: Int = 0,
    val date: Date = Date(),
    val size: Long = 0L,
    val dimensions: String = "",
    val extension: String = "",
    val palette: List<Int> = emptyList(),
    // Foreign keys
    val artistId: Int? = null
)