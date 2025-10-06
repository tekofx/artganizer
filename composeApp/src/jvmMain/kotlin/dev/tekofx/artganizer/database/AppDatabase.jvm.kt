package dev.tekofx.artganizer.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import java.io.File

actual object AppDatabaseConstructor :
    RoomDatabaseConstructor<AppDatabase> {
    actual override fun initialize(): AppDatabase {
        TODO("Not yet implemented")
    }
}

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "artganizer.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}