package dev.tekofx.artganizer.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(
            entity = Submission::class,
            parentColumns = ["id"],
            childColumns = ["submissionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["submissionId"])]
)
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Date = Date(),
    val uri: Uri = Uri.EMPTY,
    val size: Long = 0L,
    val dimensions: String = "",
    val extension: String = "",
    val palette: List<Int> = emptyList(),
    val submissionId: Int,
)