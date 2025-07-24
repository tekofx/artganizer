package dev.tekofx.artganizer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tekofx.artganizer.navigation.Navigation
import dev.tekofx.artganizer.navigation.showBottomAppBar
import dev.tekofx.artganizer.ui.components.BottomNavigationBar
import org.koin.compose.KoinApplication

@Composable
fun App(
    sharedText: String?,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    KoinApplication(
        application = {
            modules(appModules)
        })
    {
        Scaffold(
            modifier = Modifier.fillMaxSize().navigationBarsPadding(), bottomBar = {
                AnimatedVisibility(
                    enter = slideInVertically(initialOffsetY = { 2 * it }),
                    exit = slideOutVertically(targetOffsetY = { 2 * it }),
                    visible = showBottomAppBar(currentRoute)
                ) {
                    BottomNavigationBar(
                        navHostController = navController,
                    )
                }
            }) { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                Navigation(
                    navController, sharedText
                )
            }
        }

    }
}