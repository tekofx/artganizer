package dev.tekofx.artganizer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.entities.TagWithSubmissions
import kotlinx.coroutines.flow.Flow

@Dao
interface ITagDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: Tag)

    // GET
    @Query("SELECT * FROM tags WHERE tagId = :id")
    suspend fun getTagById(id: Long): TagWithSubmissions?

    @Query("SELECT * FROM tags ")
    fun getTags(): Flow<List<TagWithSubmissions>>

    // UPDATE
    @Update
    suspend fun update(tag: Tag)

    // DELETE
    @Delete
    suspend fun delete(tag: Tag)
}