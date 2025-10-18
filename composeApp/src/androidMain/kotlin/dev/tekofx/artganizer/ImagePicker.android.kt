package dev.tekofx.artganizer

import android.content.ContentResolver
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

actual fun rememberImagePicker(onImagePicked: (String) -> Unit): ImagePicker {
    return AndroidImagePicker(onImagePicked)
}

private class AndroidImagePicker(private val onImagePicked: (String) -> Unit) : ImagePicker {
    private var launcher: (() -> Unit)? = null

    @Composable
    override fun Content() {
        val context = androidx.compose.ui.platform.LocalContext.current
        val contentResolver: ContentResolver = context.contentResolver

        val launcherActivity = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                if (uri != null) {
                    val path = getPathFromUri(uri, contentResolver)
                    onImagePicked(path)
                }
            }
        )

        launcher = { launcherActivity.launch("image/*") }
    }

    override fun launch() {
        launcher?.invoke()
    }

    private fun getPathFromUri(uri: android.net.Uri, contentResolver: ContentResolver): String {
        return try {
            val cursor = contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val name = it.getString(0)
                    return "$name (from Android)"
                }
            }
            uri.toString()
        } catch (e: Exception) {
            Log.e("ImagePicker", "Failed to query file path", e)
            uri.toString()
        }
    }
}