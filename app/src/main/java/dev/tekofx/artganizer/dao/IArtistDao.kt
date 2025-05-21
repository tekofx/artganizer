package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import kotlinx.coroutines.flow.Flow

@Dao
interface IArtistDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artist: Artist)

    // GET
    @Query("SELECT * from artists WHERE artistId = :id")
    fun getArtists(id: Int): Flow<Artist>

    @Query("SELECT * from artists ORDER BY name ASC")
    fun getAllArtists(): Flow<List<Artist>>

    @Transaction
    @Query("SELECT * FROM artists WHERE artistId = :artistId")
    suspend fun getArtistWithSubmissions(artistId: Int): ArtistWithSubmissions?

    @Transaction
    @Query("SELECT * from artists ORDER BY name ASC")
    fun getAllArtistsWithSubmissions(): Flow<List<ArtistWithSubmissions>>

    // UPDATE
    @Update
    suspend fun update(artist: Artist)

    // DELETE
    @Delete
    suspend fun delete(artist: Artist)


}