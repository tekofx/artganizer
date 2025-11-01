package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.IImageDao
import dev.tekofx.artganizer.entities.Image
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.delete

class ImageRepository(
    private val imageDao: IImageDao,
) {
    suspend fun insert(image: Image) = imageDao.insert(image)

    // Delete
    suspend fun delete(image: Image) {
        imageDao.delete(image)
        val platformFile = PlatformFile(image.uri)
        platformFile.delete()
    }

}