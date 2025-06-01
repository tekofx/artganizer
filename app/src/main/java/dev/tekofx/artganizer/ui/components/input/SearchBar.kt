package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

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
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") },
        trailingIcon = {
            IconButton(
                onClick = {
                    onClear()
                    focusManager.clearFocus()
                }
            ) {
                Icon(Icons.Filled.Clear, contentDescription = "")
            }
        },
        singleLine = true,
        value = queryText,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(40.dp),
        label = { label() },
    )
}

