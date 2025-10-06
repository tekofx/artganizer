package dev.tekofx.artganizer

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.tekofx.artganizer.Platform
import dev.tekofx.artganizer.database.AppDatabase
import dev.tekofx.artganizer.database.getArtistDao
import dev.tekofx.artganizer.database.getCharacterDao
import dev.tekofx.artganizer.database.getCharacterSubmissionCrossRefDao
import dev.tekofx.artganizer.database.getImageDao
import dev.tekofx.artganizer.database.getRoomDatabase
import dev.tekofx.artganizer.database.getSubmissionDao
import dev.tekofx.artganizer.database.getTagDao
import dev.tekofx.artganizer.database.getTagSubmissionCrossRefDao
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module




val daoModule = module {
    single { getRoomDatabase(get<RoomDatabase.Builder<AppDatabase>>()) }
    single { getSubmissionDao(get()) }
    single { getArtistDao(get()) }
    single { getImageDao(get()) }
    single { getCharacterDao(get()) }
    single { getCharacterSubmissionCrossRefDao(get()) }
    single { getTagDao(get()) }
    single { getTagSubmissionCrossRefDao(get()) }
}



val appModules = listOf(
    daoModule,
)

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(daoModule)
    }
}