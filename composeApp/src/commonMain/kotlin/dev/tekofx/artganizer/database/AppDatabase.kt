package dev.tekofx.artganizer.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
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
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterSubmissionCrossRef
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.entities.TagSubmissionCrossRef
import kotlinx.coroutines.Dispatchers

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
    IntListConverter::class,
    LongListConverter::class,
    StringListConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun submissionDao(): ISubmissionDao
    abstract fun artistDao(): IArtistDao
    abstract fun imageDao(): IImageDao
    abstract fun characterDao(): ICharacterDao
    abstract fun characterSubmissionCrossRefDao(): ICharacterSubmissionCrossRef
    abstract fun tagDao(): ITagDao
    abstract fun tagSubmissionCrossRefDao(): ITagSubmissionCrossRef
}


expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

fun getSubmissionDao(appDatabase: AppDatabase) = appDatabase.submissionDao()
fun getArtistDao(appDatabase: AppDatabase) = appDatabase.artistDao()
fun getImageDao(appDatabase: AppDatabase) = appDatabase.imageDao()
fun getCharacterDao(appDatabase: AppDatabase) = appDatabase.characterDao()
fun getCharacterSubmissionCrossRefDao(appDatabase: AppDatabase) =
    appDatabase.characterSubmissionCrossRefDao()

fun getTagDao(appDatabase: AppDatabase) = appDatabase.tagDao()
fun getTagSubmissionCrossRefDao(appDatabase: AppDatabase) = appDatabase.tagSubmissionCrossRefDao()