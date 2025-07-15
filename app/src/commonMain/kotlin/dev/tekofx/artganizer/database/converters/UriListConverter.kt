package dev.tekofx.artganizer.database.converters

import androidx.room.TypeConverter
import coil3.Uri
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UriListConverter {

    @TypeConverter
    fun fromString(value: String): List<Uri> {
        val stringList: List<String> = Json.decodeFromString(value)
        val result = stringList.map { Uri.parse(it) }
        return result
    }

    @TypeConverter
    fun fromUriList(list: List<Uri>): String {
        val stringList = list.map { it.toString() }
        return Json.encodeToString(stringList)
    }
}