package dev.tekofx.artganizer.database.converters

import androidx.room.TypeConverter

class LongListConverter {
    @TypeConverter
    fun fromLongList(value: List<Long>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toLongList(value: String): List<Long> {
        return if (value.isEmpty()) emptyList() else value.split(",").map { it.toLong() }
    }
}