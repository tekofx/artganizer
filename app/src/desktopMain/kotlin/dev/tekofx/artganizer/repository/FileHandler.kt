package dev.tekofx.artganizer.repository

import java.io.File

actual class FileHandler() {
    actual fun deleteFile(fileName: String) {
        val file = File(fileName)
        if (file.exists()) {
            file.delete()
        }
    }
}