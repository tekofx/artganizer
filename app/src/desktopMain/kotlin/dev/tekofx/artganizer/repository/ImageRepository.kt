package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.IImageDao
import dev.tekofx.artganizer.entities.Image
import java.io.File

actual class ImageRepository actual constructor(private val imageDao: IImageDao) {
    actual suspend fun insert(image: Image) = imageDao.insert(image)

    actual suspend fun delete(image: Image) {
        imageDao.delete(image)
        removeImageFromStorage(image.uri)
    }
}

// Platform-specific helper
private fun removeImageFromStorage(filePath: String) {
    val file = File(filePath)
    if (file.exists()) {
        file.delete()
    }
}