package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.IImageDao
import dev.tekofx.artganizer.entities.Image

class ImageRepository(private val imageDao: IImageDao) {
    suspend fun insert(image: Image) = imageDao.insert(image)
}