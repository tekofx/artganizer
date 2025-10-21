package dev.tekofx.artganizer

import androidx.room.Room
import dev.tekofx.artganizer.database.AppDatabase
import dev.tekofx.artganizer.repository.ImageManager
import dev.tekofx.artganizer.utils.DesktopImageStorage
import dev.tekofx.artganizer.utils.ImageStorage
import org.koin.dsl.module

actual val platformModule = module {
    single {
        Room.databaseBuilder<AppDatabase>("artganizer.db")
    }
    single<ImageStorage> { DesktopImageStorage() }
    single<ImageManager> { ImageManager(get<ImageStorage>()) }

}