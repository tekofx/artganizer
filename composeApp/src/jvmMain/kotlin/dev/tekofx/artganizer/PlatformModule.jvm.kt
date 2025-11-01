package dev.tekofx.artganizer

import androidx.room.Room
import dev.tekofx.artganizer.database.AppDatabase
import dev.tekofx.artganizer.managers.DesktopUiManager
import dev.tekofx.artganizer.repository.ImageManager
import dev.tekofx.artganizer.utils.DesktopImageStorage
import dev.tekofx.artganizer.utils.ImageStorage
import dev.tekofx.artganizer.viewmodel.DesktopUiViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule = module {
    single {
        Room.databaseBuilder<AppDatabase>("artganizer.db")
    }
    single<DesktopUiManager> { DesktopUiManager() } // Provide the dependency first
    single<ImageStorage> { DesktopImageStorage() }
    single<ImageManager> { ImageManager(get<ImageStorage>()) }
    viewModelOf(::DesktopUiViewModel)
}

