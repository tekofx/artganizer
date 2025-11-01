package dev.tekofx.artganizer.navigation

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer


@Serializable
open class AppRoute


@OptIn(InternalSerializationApi::class)
fun AppRoute.serialName() = this::class.serializer().descriptor.serialName


// Android Route
@Serializable
object ArtistsListRoute : AppRoute()

@Serializable
data class ArtistDetailsRoute(val id: Long) : AppRoute()

@Serializable
object ArtistCreationRoute : AppRoute()

@Serializable
object CharactersListRoute : AppRoute()

@Serializable
data class CharacterDetailsRoute(val id: Long) : AppRoute()

@Serializable
object CharacterCreationRoute : AppRoute()

@Serializable
object SubmissionsListRoute : AppRoute()

@Serializable
data class SubmissionDetailsRoute(val id: Long) : AppRoute()

@Serializable
object SubmissionCreationRoute : AppRoute()

@Serializable
object TagsListRoute : AppRoute()

@Serializable
data class TagDetailsRoute(val id: Long) : AppRoute()

@Serializable
object TagCreationRoute : AppRoute()

// Desktop routes
@Serializable
object DesktopMainRoute : AppRoute()