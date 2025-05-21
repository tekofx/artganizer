package dev.tekofx.artganizer.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey(autoGenerate = true)
    val artistId: Long = 0,
    val name: String,
    val imagePath: Uri?,
    val socialNetworks: List<String>,
)