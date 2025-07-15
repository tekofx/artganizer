package dev.tekofx.artganizer.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true)
    val characterId: Long = 0,
    val name: String,
    val species: String?,
    val gender: String?,
    val pronouns: String?,
    val height: String?,
    val description: String?,
    val imagePath: String? = null,
)