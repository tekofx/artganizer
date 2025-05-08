package dev.tekofx.artganizer.repository

import android.util.Log
import java.net.UnknownHostException

class GalleryRepository() {
    fun getImages(): List<String> {
        try {
            val images = listOf(
                "https://example.com/image1.jpg",
                "https://example.com/image2.jpg",
                "https://example.com/image3.jpg"
            )
            return images
        } catch (e: UnknownHostException) {
            Log.e("GalleryRepository", "Error getting images $e")
            throw e
        } catch (e: Exception) {
            Log.e("GalleryRepository", "Error getting images $e")
            throw e
        }
    }

}