package dev.tekofx.artganizer.utils

import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun stringToDate(dateString: String, format: String = "dd/MM/yyyy"): Date? {
    return try {
        if (dateString.isEmpty()) {
            return null
        }
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.parse(dateString)
    } catch (e: Exception) {
        null
    }
}

fun dateToString(date: LocalDate, format: String = "dd/MM/yyyy"): String {
    return try {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}