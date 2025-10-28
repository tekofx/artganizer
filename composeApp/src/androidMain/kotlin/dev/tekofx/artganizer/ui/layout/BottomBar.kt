package dev.tekofx.artganizer.ui.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomBar(
    navController: NavHostController,
    showBottomNavBar: Boolean,
    actions: List<@Composable () -> Unit>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
            .animateContentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = showBottomNavBar,

                ) {
                BottomNavBar(navController)
            }
            if (!showBottomNavBar) {
                Spacer(modifier = Modifier.width(10.dp))
            }

            ActionsBar(actions)
        }
    }
}




