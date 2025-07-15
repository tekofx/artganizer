package dev.tekofx.artganizer.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(
            entity = Submission::class,
            parentColumns = ["submissionId"],
            childColumns = ["submissionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["submissionId"])]
)
data class Image @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long = 0,
    val date: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val uri: String = "",
    val size: Long = 0L,
    val dimensions: String = "",
    val extension: String = "",
    val palette: List<Int> = emptyList(),
    val submissionId: Long,
)