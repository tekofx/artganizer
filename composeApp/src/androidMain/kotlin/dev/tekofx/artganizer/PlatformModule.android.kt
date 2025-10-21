package dev.tekofx.artganizer

import android.content.Context
import dev.tekofx.artganizer.database.getDatabaseBuilder
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.utils.AndroidImageStorage
import dev.tekofx.artganizer.utils.ImageStorage
import org.koin.dsl.module

actual val platformModule = module {
    single { getDatabaseBuilder(get<Context>()) }
    single<ImageStorage> { AndroidImageStorage(get<Context>()) }
    single<ImageRepository> { ImageRepository(get<ImageStorage>()) }
}