package dev.tekofx.artganizer.repository

import android.content.Context
import java.io.File

actual class FileHandler(private val context: Context) {
    actual fun deleteFile(fileName: String) {
        val file = File(context.cacheDir, fileName)
        if (file.exists()) {
            file.delete()
        }
    }
}