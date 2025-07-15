package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.IArtistDao
import dev.tekofx.artganizer.entities.Artist

class ArtistRepository(private val artistsDao: IArtistDao) {

    // INSERT
    suspend fun insertArtist(artist: Artist) = artistsDao.insert(artist)

    // GET
    fun getAllArtistsWithSubmissions() =
        artistsDao.getAllArtistsWithSubmissions()

    suspend fun getArtistWithSubmissions(artistId: Long) =
        artistsDao.getArtistWithSubmissions(artistId)

    // UPDATE
    suspend fun updateArtist(artist: Artist) = artistsDao.update(artist)

    // DELETE
    suspend fun deleteArtist(artist: Artist) = artistsDao.delete(artist)


}