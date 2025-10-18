package dev.tekofx.artganizer.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

actual interface ImageStorage {
    actual suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit>
    actual suspend fun deleteImage(name: String): Result<Unit>
    actual suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String?
    actual suspend fun getImagePath(name: String): String?
}



@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class DesktopImageStorage : ImageStorage {
    private val storageDir = File(System.getProperty("user.home"), ".yourapp/images")
        .apply { if (!exists()) mkdirs() }

    override suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                File(storageDir, name).writeBytes(bytes)
            }
        }

    override suspend fun deleteImage(name: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val file = File(storageDir, name)
                if (file.exists()) file.delete() else throw FileNotFoundException(name) as Throwable
            }
        }.map { }

    override suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String? =
        withContext(Dispatchers.IO) {
            val file = File(storageDir, outputName)
            file.writeBytes(bytes)
            file.absolutePath
        }

    override suspend fun getImagePath(name: String): String? =
        withContext(Dispatchers.IO) {
            File(storageDir, name).takeIf { it.exists() }?.absolutePath
        }
}