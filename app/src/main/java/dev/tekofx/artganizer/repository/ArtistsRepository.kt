package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.IArtistDao
import dev.tekofx.artganizer.entities.Artist

class ArtistsRepository(private val artistsDao: IArtistDao) {

    suspend fun insertArtist(artist: Artist) = artistsDao.insert(artist)

    suspend fun deleteArtist(artist: Artist) = artistsDao.delete(artist)

    suspend fun updateArtist(artist: Artist) = artistsDao.update(artist)

    fun getAllArtists() = artistsDao.getAllArtists()

    fun getArtistWithSubmissions(artistId: Int) =
        artistsDao.getArtistWithSubmissions(artistId)

}