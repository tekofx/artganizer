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
object ArtistsList : AppRoute()

@Serializable
data class ArtistDetails(val id: Long) : AppRoute()

@Serializable
object ArtistCreation : AppRoute()

@Serializable
object CharactersList : AppRoute()

@Serializable
data class CharacterDetails(val id: Long) : AppRoute()

@Serializable
object CharacterCreation : AppRoute()

@Serializable
object SubmissionsList : AppRoute()

@Serializable
data class SubmissionDetails(val id: Long) : AppRoute()

@Serializable
object SubmissionCreation : AppRoute()

@Serializable
object TagsList : AppRoute()

@Serializable
data class TagDetails(val id: Long) : AppRoute()

@Serializable
object TagCreation : AppRoute()

// Desktop routes
@Serializable
object DesktopMainRoute : AppRoute()