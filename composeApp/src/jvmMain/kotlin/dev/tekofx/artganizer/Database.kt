package dev.tekofx.artganizer

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.tekofx.artganizer.database.AppDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "artganizer.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}