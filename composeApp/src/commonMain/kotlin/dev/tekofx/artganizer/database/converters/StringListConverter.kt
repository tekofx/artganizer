package dev.tekofx.artganizer.database.converters

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> =
        if (value.isEmpty()) emptyList() else value.split(",")

    @TypeConverter
    fun toString(list: List<String>): String = list.joinToString(",")
}