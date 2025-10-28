package dev.tekofx.artganizer.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomBar(
    navController: NavHostController,
    actions: List<@Composable () -> Unit>

) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavBar(navController)
        ActionsBar(actions)
    }
}




