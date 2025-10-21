package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.IImageDao
import dev.tekofx.artganizer.entities.Image

class ImageRepository(
    private val imageDao: IImageDao,
    private val imageManager: ImageManager
) {
    suspend fun insert(image: Image) = imageDao.insert(image)

    // Delete
    suspend fun delete(image: Image) {
        imageDao.delete(image)
        imageManager.removeImage(image.uri)
    }

}