package dev.tekofx.artganizer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.tekofx.artganizer.dao.IArtistDao
import dev.tekofx.artganizer.dao.ISubmissionDao
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Submission

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Submission::class, Artist::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun submissionDao(): ISubmissionDao
    abstract fun artistDao(): IArtistDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
