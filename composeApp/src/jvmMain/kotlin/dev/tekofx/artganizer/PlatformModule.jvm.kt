package dev.tekofx.artganizer

import androidx.room.Room
import dev.tekofx.artganizer.database.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule = module {
    single {
        Room.databaseBuilder<AppDatabase>("artganizer.db")
    }
}