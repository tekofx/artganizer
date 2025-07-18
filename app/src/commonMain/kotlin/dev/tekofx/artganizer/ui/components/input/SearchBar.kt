package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.calendar_outlined
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit) = { Text("Search") },
    queryText: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.calendar_outlined),
                contentDescription = ""
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onClear()
                    focusManager.clearFocus()
                }
            ) {
                Icon(painterResource(Res.drawable.calendar_outlined), contentDescription = "")
            }
        },
        singleLine = true,
        value = queryText,
        onValueChange = { onValueChange(it) },
        shape = MaterialTheme.shapes.extraLarge,
        //label = { label() },
    )
}

