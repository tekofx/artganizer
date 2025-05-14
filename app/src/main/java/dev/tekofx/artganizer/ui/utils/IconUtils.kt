package dev.tekofx.artganizer.ui.utils

import dev.tekofx.artganizer.R

fun getSocialNetworkIconRes(socialNetwork: String): Int {
    return when {
        socialNetwork.contains("twitter.com", ignoreCase = true) -> R.drawable.brand_x
        socialNetwork.contains("x.com", ignoreCase = true) -> R.drawable.brand_x
        socialNetwork.contains("instagram.com", ignoreCase = true) -> R.drawable.brand_instagram
        socialNetwork.contains("t.me", ignoreCase = true) -> R.drawable.brand_telegram
        socialNetwork.contains("discord", ignoreCase = true) -> R.drawable.brand_discord
        socialNetwork.contains("bsky.app", ignoreCase = true) -> R.drawable.brand_bluesky
        socialNetwork.contains("furaffinity.net", ignoreCase = true) -> R.drawable.brand_furaffinity
        socialNetwork.contains("tiktok.com", ignoreCase = true) -> R.drawable.brand_tiktok

        else -> R.drawable.world
    }
}