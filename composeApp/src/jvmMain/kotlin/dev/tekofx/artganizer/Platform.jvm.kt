package dev.tekofx.artganizer

import androidx.room.Room
import dev.tekofx.artganizer.database.AppDatabase
import org.koin.dsl.module

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

