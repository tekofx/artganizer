package dev.tekofx.artganizer.repository

import android.content.Context
import dev.tekofx.artganizer.dao.IImageDao
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.utils.removeImageFromInternalStorage

class ImageRepository(private val imageDao: IImageDao) {
    suspend fun insert(image: Image) = imageDao.insert(image)

    // Delete
    suspend fun delete(context: Context, image: Image) {
        imageDao.delete(image)
        removeImageFromInternalStorage(context, image.uri)
    }

}