package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.tekofx.artganizer.entities.Image

@Dao
interface IImageDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Image)


    // DELETE
    @Delete
    suspend fun delete(image: Image)
}