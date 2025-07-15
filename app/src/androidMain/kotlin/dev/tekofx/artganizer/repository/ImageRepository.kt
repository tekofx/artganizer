package dev.tekofx.artganizer.repository

import android.content.Context
import dev.tekofx.artganizer.dao.IImageDao
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.utils.removeImageFromInternalStorage
import java.io.File

actual class ImageRepository(private val imageDao: IImageDao) {
    suspend fun insert(image: Image) = imageDao.insert(image)

    suspend fun delete(context: Context, image: Image) {
        imageDao.delete(image)
        removeImageFromInternalStorage(context, image.uri)
    }
}

// Platform-specific helper
private fun removeImageFromInternalStorage(context: Context, uri: String) {
    val file = File(uri)
    if (file.exists()) {
        file.delete()
    }
}