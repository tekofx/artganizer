package dev.tekofx.artganizer.repository

import java.io.File

actual class FileHandler() {
    actual fun deleteFile(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
    }
}