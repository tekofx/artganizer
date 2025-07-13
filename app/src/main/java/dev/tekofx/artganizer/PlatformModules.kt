package dev.tekofx.artganizer

import dev.tekofx.artganizer.database.AppDatabase
import dev.tekofx.artganizer.database.getArtistDao
import dev.tekofx.artganizer.database.getCharacterDao
import dev.tekofx.artganizer.database.getCharacterSubmissionCrossRefDao
import dev.tekofx.artganizer.database.getImageDao
import dev.tekofx.artganizer.database.getSubmissionDao
import dev.tekofx.artganizer.database.getTagDao
import dev.tekofx.artganizer.database.getTagSubmissionCrossRefDao
import dev.tekofx.artganizer.repository.ArtistRepository
import dev.tekofx.artganizer.repository.CharactersRepository
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.repository.TagRepository
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val repositoryModule = module {
    singleOf(::ArtistRepository)
    singleOf(::CharactersRepository)
    singleOf(::ImageRepository)
    singleOf(::SubmissionRepository)
    singleOf(::TagRepository)
}

val daoModule = module {
    single { AppDatabase.getDatabase(get()) }
    single { getSubmissionDao(get()) }
    single { getArtistDao(get()) }
    single { getImageDao(get()) }
    single { getCharacterDao(get()) }
    single { getCharacterSubmissionCrossRefDao(get()) }
    single { getTagDao(get()) }
    single { getTagSubmissionCrossRefDao(get()) }
}

val viewModelModule = module {
    viewModelOf(::ArtistsViewModel)
    viewModelOf(::CharactersViewModel)
    viewModelOf(::SubmissionsViewModel)
    viewModelOf(::TagsViewModel)
}

val appModules = listOf(
    daoModule,
    repositoryModule,
    viewModelModule
)