package dev.tekofx.artganizer


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform