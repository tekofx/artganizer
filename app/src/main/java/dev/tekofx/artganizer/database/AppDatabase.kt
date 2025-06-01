package dev.tekofx.artganizer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.tekofx.artganizer.dao.IArtistDao
import dev.tekofx.artganizer.dao.ICharacterDao
import dev.tekofx.artganizer.dao.ICharacterSubmissionCrossRef
import dev.tekofx.artganizer.dao.IImageDao
import dev.tekofx.artganizer.dao.ISubmissionDao
import dev.tekofx.artganizer.dao.ITagDao
import dev.tekofx.artganizer.dao.ITagSubmissionCrossRef
import dev.tekofx.artganizer.database.converters.DateConverter
import dev.tekofx.artganizer.database.converters.IntListConverter
import dev.tekofx.artganizer.database.converters.LongListConverter
import dev.tekofx.artganizer.database.converters.StringListConverter
import dev.tekofx.artganizer.database.converters.UriListConverter
import dev.tekofx.artganizer.database.converters.UriStringConverter
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterSubmissionCrossRef
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.entities.TagSubmissionCrossRef

/**
 * Database class with a singleton Instance object.
 */
@Database(
    entities = [
        Submission::class,
        Artist::class,
        Image::class,
        Character::class,
        CharacterSubmissionCrossRef::class,
        Tag::class,
        TagSubmissionCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    StringListConverter::class,
    IntListConverter::class,
    UriListConverter::class,
    UriStringConverter::class,
    LongListConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun submissionDao(): ISubmissionDao
    abstract fun artistDao(): IArtistDao
    abstract fun imageDao(): IImageDao
    abstract fun characterDao(): ICharacterDao
    abstract fun characterSubmissionCrossRefDao(): ICharacterSubmissionCrossRef
    abstract fun tagDao(): ITagDao
    abstract fun tagSubmissionCrossRefDao(): ITagSubmissionCrossRef

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
