package dev.tekofx.artganizer.database.converters

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UriListConverter {
    @TypeConverter
    fun fromString(value: String): List<Uri> {
        val listType = object : TypeToken<List<String>>() {}.type
        val stringList: List<String> = Gson().fromJson(value, listType)
        val result = stringList.map { it.toUri() }
        Log.d("fromString", result.toString())
        return result
    }

    @TypeConverter
    fun fromUriList(list: List<Uri>): String {
        val stringList = list.map { it.toString() }
        return Gson().toJson(stringList)
    }
}