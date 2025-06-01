package dev.tekofx.artganizer.ui.screens.tags

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.TagWithSubmissions
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.submission.Gallery
import dev.tekofx.artganizer.ui.components.tags.TagForm
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.toTagWithSubmissions
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagDetailsScreen(
    tagsViewModel: TagsViewModel,
    navHostController: NavHostController
) {
    val showPopup by tagsViewModel.showPopup.collectAsState()
    val showTagEdit by tagsViewModel.showTagEdit.collectAsState()
    val scope = rememberCoroutineScope()

    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                tagsViewModel.setShowPopup(true)
                tagsViewModel.deleteTag(tagsViewModel.currentTagUiState)
                navHostController.popBackStack()
                tagsViewModel.setShowPopup(false)
            },
            onDismiss = {
                tagsViewModel.setShowPopup(false)
            }
        )
    }
    Scaffold {
        if (showTagEdit) {
            TagForm(
                tagsViewModel.currentTagUiState,
                onItemValueChange = { newValue -> tagsViewModel.updateCurrentUiState(newValue) },
                onSaveClick = {
                    scope.launch { tagsViewModel.editTag() }
                    tagsViewModel.setShowEditArtist(false)
                },
                onCancelClick = {
                    tagsViewModel.setShowEditArtist(false)
                },
            )
        } else {
            TagInfo(
                tagWithSubmissions = tagsViewModel.currentTagUiState.toTagWithSubmissions(),
                onEditClick = {
                    tagsViewModel.setShowEditArtist(true)
                },
                onDeleteClick = {
                    tagsViewModel.setShowPopup(true)
                },
                onImageClick = { submissionId ->
                    navHostController.navigate(
                        "${NavigateDestinations.SUBMISSIONS_SCREEN}/$submissionId"
                    )
                },
            )
        }
    }
}

@Composable
fun TagInfo(
    tagWithSubmissions: TagWithSubmissions,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onImageClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    IconResource.fromDrawableResource(R.drawable.tag_filled).asPainterResource(),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = tagWithSubmissions.tag.name,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ButtonWithIcon(
                    onClick = onEditClick,
                    text = "Edit",
                    iconResource = IconResource.fromDrawableResource(R.drawable.edit),
                )
                ButtonWithIcon(
                    onClick = onDeleteClick,
                    text = "Delete",
                    iconResource = IconResource.fromDrawableResource(R.drawable.trash),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Set a fixed height for the grid
            ) {
                Gallery(
                    tagWithSubmissions.submissions,
                    onImageClick = {
                        onImageClick(it)
                    }
                )
            }
        }
    }
}
