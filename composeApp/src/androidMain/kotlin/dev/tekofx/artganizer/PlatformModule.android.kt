package dev.tekofx.artganizer

import android.content.Context
import dev.tekofx.artganizer.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule = module {
    single { getDatabaseBuilder(get<Context>()) }
}