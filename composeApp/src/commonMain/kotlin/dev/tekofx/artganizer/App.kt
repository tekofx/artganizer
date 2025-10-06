package dev.tekofx.artganizer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.compose_multiplatform
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<TagsViewModel>()
    KoinApplication(
        application = {
            modules()
        }
    ) {
        MaterialTheme {
            var showContent by remember { mutableStateOf(false) }
            val tags by viewModel.tags.collectAsState()
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                Text(viewModel.newTagUiState.tagDetails.name)
                Text( "Tags in DB: ${tags.size}" )


                TextField(
                    value = viewModel.newTagUiState.tagDetails.name,
                    onValueChange = {
                        viewModel.updateNewUiState(viewModel.newTagUiState.tagDetails.copy(name = it))
                    }
                )
                Button(onClick = {
                    runBlocking {
                        viewModel.saveTag()
                    }
                }) {
                    Text("Save")
                }

                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }
            }
        }
    }
}
