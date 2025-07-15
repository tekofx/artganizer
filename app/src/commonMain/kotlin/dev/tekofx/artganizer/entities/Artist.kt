package dev.tekofx.artganizer.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey(autoGenerate = true)
    val artistId: Long = 0,
    val name: String,
    val imagePath: String?,
    val socialNetworks: List<String>,
)


fun Artist.getSocialShareText(): String {
    return buildString {
        append("\uD83C\uDFA8: ")
        if (socialNetworks.isNotEmpty()) {
            append(socialNetworks.first())
        } else {
            append(name)
        }
    }
}