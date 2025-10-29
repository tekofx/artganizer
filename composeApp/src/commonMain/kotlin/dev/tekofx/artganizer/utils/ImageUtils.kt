package dev.tekofx.artganizer.utils

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.ImageFormat
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.compressImage
import io.github.vinceglb.filekit.div
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.readBytes
import io.github.vinceglb.filekit.write
import java.util.UUID


suspend fun saveThumbnailFromPlatformFile(file: PlatformFile): PlatformFile {
    return saveThumbnail(file)
}

suspend fun saveThumbnailFromPath(path: String): PlatformFile {
    val file = PlatformFile(path)
    return saveThumbnail(file)
}

private suspend fun saveThumbnail(file: PlatformFile): PlatformFile {
    // Save thumbnail
    val compressedBytes = FileKit.compressImage(
        bytes = file.readBytes(),
        quality = 80, // 0-100, where 100 is highest quality
        maxWidth = 500, // Optional maximum width
        maxHeight = 500, // Optional maximum height
        imageFormat = ImageFormat.JPEG // JPEG or PNG
    )
    val thumbnailFile =
        PlatformFile(FileKit.filesDir, "thumbnail_${UUID.randomUUID()}.jpg")
    thumbnailFile.write(compressedBytes)
    return thumbnailFile

}

suspend fun saveSubmissionFromPlatformFile(file: PlatformFile): PlatformFile {
    val name = "submission_${UUID.randomUUID()}.${file.extension}"
    val destinationFile = FileKit.filesDir / name
    destinationFile.write(file)

    return destinationFile
}