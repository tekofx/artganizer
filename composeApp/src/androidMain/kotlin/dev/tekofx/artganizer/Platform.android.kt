package dev.tekofx.artganizer

import android.os.Build
import androidx.room.Room
import dev.tekofx.artganizer.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

val androidModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "artganizer.db"
        )
    }
}