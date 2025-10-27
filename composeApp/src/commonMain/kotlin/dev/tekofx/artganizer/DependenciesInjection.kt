package dev.tekofx.artganizer

import androidx.room.RoomDatabase
import dev.tekofx.artganizer.database.AppDatabase
import dev.tekofx.artganizer.database.getArtistDao
import dev.tekofx.artganizer.database.getCharacterDao
import dev.tekofx.artganizer.database.getCharacterSubmissionCrossRefDao
import dev.tekofx.artganizer.database.getImageDao
import dev.tekofx.artganizer.database.getRoomDatabase
import dev.tekofx.artganizer.database.getSubmissionDao
import dev.tekofx.artganizer.database.getTagDao
import dev.tekofx.artganizer.database.getTagSubmissionCrossRefDao
import dev.tekofx.artganizer.navigation.NavigationManager
import dev.tekofx.artganizer.navigation.NavigationViewModel
import dev.tekofx.artganizer.repository.ArtistRepository
import dev.tekofx.artganizer.repository.CharactersRepository
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.repository.TagRepository
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val daoModule = module {
    single { getRoomDatabase(get<RoomDatabase.Builder<AppDatabase>>()) }
    single { getSubmissionDao(get()) }
    single { getArtistDao(get()) }
    single { getImageDao(get()) }
    single { getCharacterDao(get()) }
    single { getCharacterSubmissionCrossRefDao(get()) }
    single { getTagDao(get()) }
    single { getTagSubmissionCrossRefDao(get()) }
}

val viewModelModule = module {
    viewModelOf(::TagsViewModel)
    viewModelOf(::ArtistsViewModel)
    viewModelOf(::NavigationViewModel)
    viewModelOf(::CharactersViewModel)
    viewModelOf(::SubmissionsViewModel)

}

val repositoryModule = module {
    singleOf(::TagRepository)
    singleOf(::ArtistRepository)
    singleOf(::NavigationManager)
    singleOf(::CharactersRepository)
    singleOf(::SubmissionRepository)
    singleOf(::ImageRepository)

}
val appModules = listOf(
    platformModule,
    daoModule,
    repositoryModule,
    viewModelModule,
)

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModules)
    }
}
