import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun AutoCompleteComboBox(
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val filteredItems = items.filter { it.contains(query, ignoreCase = true) }

    Column {
        TextField(
            value = query,
            onValueChange = {
                query = it
                expanded = it.isNotEmpty()
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        query = item
                        expanded = false
                        onItemSelected(item)
                    },
                    text = { Text(item) }
                )
            }
        }
    }
}