package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.entities.ImageInfo
import dev.tekofx.artganizer.utils.ImageStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ImageManager(private val storage: ImageStorage) {

    suspend fun saveImageFromPath(imagePath: String, name: String): Result<Unit> {
        val bytes = loadImageBytes(imagePath) // You need to implement this per platform
        return storage.saveImage(bytes, name)
    }

    suspend fun removeImage(name: String): Result<Unit> =
        storage.deleteImage(name)

    suspend fun saveThumbnail(imagePath: String, outputName: String): String? {
        val bytes = loadImageBytes(imagePath)
        return storage.saveThumbnail(bytes, outputName)
    }

    // Platform-specific function to read image bytes from a path
    private suspend fun loadImageBytes(path: String): ByteArray {
        // This could also be abstracted via expect/actual if needed
        return withContext(Dispatchers.IO) {
            File(path).readBytes()
        }
    }

    suspend fun getImageInfo(path: String): ImageInfo? {
        return storage.getImageInfo(path)
    }

    suspend fun getColorPalette(path: String): List<Int> {
        return storage.getColorpalette(path)
    }
}
