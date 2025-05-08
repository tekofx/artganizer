package dev.tekofx.artganizer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.tekofx.artganizer.dao.ISubmissionDao
import dev.tekofx.artganizer.entities.Submission

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Submission::class], version = 1, exportSchema = false)
abstract class SubmissionDatabase : RoomDatabase() {

    abstract fun submissionDao(): ISubmissionDao

    companion object {
        @Volatile
        private var Instance: SubmissionDatabase? = null

        fun getDatabase(context: Context): SubmissionDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SubmissionDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
