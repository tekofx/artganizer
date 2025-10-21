package dev.tekofx.artganizer.utils

// androidMain/kotlin/ImageStorage.android.kt
import android.content.Context
import dev.tekofx.artganizer.entities.ImageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

actual interface ImageStorage {
    actual suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit>
    actual suspend fun deleteImage(name: String): Result<Unit>
    actual suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String?
    actual suspend fun getImagePath(name: String): String?
    actual suspend fun getImageInfo(bytes: ByteArray): ImageInfo?
}

@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class AndroidImageStorage(private val context: Context) : ImageStorage {

    private val imagesDir: File
        get() = File(context.filesDir, "images").apply { if (!exists()) mkdirs() }

    override suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                File(imagesDir, name).writeBytes(bytes)
            }
        }

    override suspend fun deleteImage(name: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val file = File(imagesDir, name)
                if (file.exists()) file.delete() else throw FileNotFoundException(name) as Throwable
            }
        }.map { } // Convert success value (Boolean) to Unit

    override suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String? =
        withContext(Dispatchers.IO) {
            val file = File(imagesDir, outputName)
            file.writeBytes(bytes)
            file.absolutePath
        }

    override suspend fun getImagePath(name: String): String? =
        withContext(Dispatchers.IO) {
            File(imagesDir, name).takeIf { it.exists() }?.absolutePath
        }

    override suspend fun getImageInfo(bytes: ByteArray): ImageInfo? {
        // Implement image info extraction logic here if needed
        return null
    }
}