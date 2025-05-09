package dev.tekofx.artganizer.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun stringToDate(dateString: String, format: String = "dd/MM/yyyy"): Date? {
    return try {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.parse(dateString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun dateToString(date: Date, format: String = "dd/MM/yyyy"): String {
    return try {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}