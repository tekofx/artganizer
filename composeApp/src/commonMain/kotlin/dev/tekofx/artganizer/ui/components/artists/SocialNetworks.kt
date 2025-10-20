package dev.tekofx.artganizer.ui.components.artists

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.utils.getSocialNetworkIconRes
import org.jetbrains.compose.resources.painterResource

@Composable
fun SocialNetworks(
    socialNetworks: List<String>,
) {
    LazyRow {
        items(socialNetworks) {
            SocialNetworkItem(
                socialNetwork = it,
            )
        }
    }
}

@Composable
fun SocialNetworkItem(
    socialNetwork: String,
) {

    IconButton(
        onClick = {
            // TODO: Implement link opening

        },
        modifier = Modifier.size(50.dp)
    ) {
        Icon(
            painter = painterResource(getSocialNetworkIconRes(socialNetwork)),
            contentDescription = "Social Network Icon",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
        )
    }

}