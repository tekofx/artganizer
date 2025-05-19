package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.tekofx.artganizer.entities.Image

@Dao
interface IImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Image)

}