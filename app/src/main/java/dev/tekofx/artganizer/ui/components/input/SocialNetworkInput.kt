package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.utils.getSocialNetworkIconRes

@Composable
fun SocialNetworkInput(
    socialNetworks: List<String>,
    onAddSocialNetwork: (String) -> Unit,
    onRemoveSocialNetwork: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Social Networks", style = MaterialTheme.typography.titleLarge)
        // Display existing social networks
        SocialNetworksList(
            socialNetworks = socialNetworks,
            onRemoveSocialNetwork = { url ->
                // Handle removal of social network
                onRemoveSocialNetwork(url)
            },
        )

        // Input field to add a new social network
        var newSocialNetwork by remember { mutableStateOf("") }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = newSocialNetwork,
                onValueChange = { newSocialNetwork = it },
                label = { Text("Add Social Network") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = IconResource.fromDrawableResource(
                            getSocialNetworkIconRes(
                                newSocialNetwork
                            )
                        )
                            .asPainterResource(),
                        contentDescription = "Social Network Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
            Button(
                onClick = {
                    if (newSocialNetwork.isNotBlank()) {
                        onAddSocialNetwork(newSocialNetwork)
                        newSocialNetwork = ""
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Add")
            }
        }
    }
}

@Composable
fun SocialNetworksList(
    socialNetworks: List<String>,
    onRemoveSocialNetwork: (String) -> Unit,
) {
    socialNetworks.forEach { url ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(
                painter = IconResource.fromDrawableResource(getSocialNetworkIconRes(url))
                    .asPainterResource(),
                contentDescription = "Social Network Icon",
                modifier = Modifier.size(35.dp)
            )
            Text(
                text = url,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
            IconButton(onClick = { onRemoveSocialNetwork(url) }) {
                Icon(
                    painter = painterResource(id = R.drawable.x), // Replace with your drawable resource
                    contentDescription = "Example Icon",
                    modifier = Modifier.size(20.dp) // Optional: Adjust size
                )
            }
        }
    }
}

@Preview
@Composable
fun SocialNetworkInputPreview() {
    val socialNetworks = listOf(
        "https://twitter.com/username",
        "https://instagram.com/username",
        "https://t.me/username"
    )
    SocialNetworkInput(
        socialNetworks = socialNetworks,
        onAddSocialNetwork = {},
        onRemoveSocialNetwork = {},
    )
}