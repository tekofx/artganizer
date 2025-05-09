package dev.tekofx.artganizer.utils

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import java.io.FileNotFoundException
import java.util.UUID

fun saveImageToInternalStorage(context: Context, uri: Uri): Uri {
    val mimeType = context.contentResolver.getType(uri)
    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
    val uniqueFilename = "${UUID.randomUUID()}.$extension"
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = context.filesDir.resolve(uniqueFilename)
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return Uri.fromFile(file)
}


fun getPathFromUri(context: Context, uri: Uri): String? {
    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        val fileName = it.getString(nameIndex)
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = context.filesDir.resolve(fileName)
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        file.absolutePath
    }
}


fun getImageInfo(context: Context, uri: Uri): ImageInfo? {
    val contentResolver = context.contentResolver

    try {
        // Get size in MB
        val fileDescriptor = contentResolver.openFileDescriptor(uri, "r") ?: return null
        val fileSizeInBytes = fileDescriptor.statSize
        val sizeInMb = String.format("%.2f", fileSizeInBytes / (1024.0 * 1024.0)).toDouble()

        // Get extension
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        val fileType = mimeType?.split("/")?.lastOrNull()?.uppercase() ?: "unknown"

        // Get dimensions
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeStream(inputStream, null, options)
        val dimensions = Pair(options.outWidth, options.outHeight)
        fileDescriptor.close()

        return ImageInfo(sizeInMb, fileType, dimensions)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    }
}

data class ImageInfo(
    val sizeInMb: Double,
    val extension: String,
    val dimensions: Pair<Int, Int>
)
