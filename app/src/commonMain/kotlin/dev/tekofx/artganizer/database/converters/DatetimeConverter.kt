package dev.tekofx.artganizer.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class DatetimeConverter {
    @TypeConverter
    fun fromDate(value: LocalDate?): Long? {
        return value?.toEpochDays()
    }

    @TypeConverter
    fun dateFromLong(value: Long?): LocalDate? {
        return value?.let { LocalDate.fromEpochDays(it) }
    }
}