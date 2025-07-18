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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.x
import dev.tekofx.artganizer.ui.utils.getSocialNetworkIconRes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SocialNetworkInput(
    socialNetworks: List<String>,
    onAddSocialNetwork: (String) -> Unit,
    onRemoveSocialNetwork: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Social Networks", style = MaterialTheme.typography.titleLarge)
        SocialNetworksList(
            socialNetworks = socialNetworks,
            onRemoveSocialNetwork = { url ->
                onRemoveSocialNetwork(url)
            },
        )

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
                        painterResource(
                            getSocialNetworkIconRes(
                                newSocialNetwork
                            )
                        ),
                        contentDescription = "Social Network Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
            Button(
                enabled = newSocialNetwork.isNotBlank(),
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
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(getSocialNetworkIconRes(url)),
                    contentDescription = "Social Network Icon",
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = url,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { onRemoveSocialNetwork(url) }) {
                Icon(
                    painter = painterResource(Res.drawable.x),
                    contentDescription = "Remove Icon",
                    modifier = Modifier.size(30.dp)
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
        "https://t.me/username",
        "https://bsky.app/profile/lissetvaras.bsky.social"
    )
    SocialNetworkInput(
        socialNetworks = socialNetworks,
        onAddSocialNetwork = {},
        onRemoveSocialNetwork = {},
    )
}