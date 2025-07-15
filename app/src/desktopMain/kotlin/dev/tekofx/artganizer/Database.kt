package dev.tekofx.artganizer

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.tekofx.artganizer.database.AppDatabase
import org.koin.dsl.module
import java.io.File

actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder().build() }
}

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "my_room.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}