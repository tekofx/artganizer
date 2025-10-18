package dev.tekofx.artganizer.utils

import androidx.annotation.DrawableRes
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.brand_bluesky
import artganizer.composeapp.generated.resources.brand_discord
import artganizer.composeapp.generated.resources.brand_furaffinity
import artganizer.composeapp.generated.resources.brand_instagram
import artganizer.composeapp.generated.resources.brand_telegram
import artganizer.composeapp.generated.resources.brand_tiktok
import artganizer.composeapp.generated.resources.brand_x
import artganizer.composeapp.generated.resources.world
import org.jetbrains.compose.resources.DrawableResource

fun getSocialNetworkIconRes(socialNetwork: String): DrawableResource {
    return when {
        socialNetwork.contains("twitter.com", ignoreCase = true) -> Res.drawable.brand_discord
        socialNetwork.contains("x.com", ignoreCase = true) -> Res.drawable.brand_x
        socialNetwork.contains("instagram.com", ignoreCase = true) -> Res.drawable.brand_instagram
        socialNetwork.contains("t.me", ignoreCase = true) -> Res.drawable.brand_telegram
        socialNetwork.contains("discord", ignoreCase = true) -> Res.drawable.brand_discord
        socialNetwork.contains("bsky.app", ignoreCase = true) -> Res.drawable.brand_bluesky
        socialNetwork.contains("furaffinity.net", ignoreCase = true) -> Res.drawable.brand_furaffinity
        socialNetwork.contains("tiktok.com", ignoreCase = true) -> Res.drawable.brand_tiktok

        else -> Res.drawable.world
    }
}