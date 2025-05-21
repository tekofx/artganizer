package dev.tekofx.artganizer.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true)
    val characterId: Long = 0,
    val name: String,
    val species: String?,
    val imagePath: Uri?,
)