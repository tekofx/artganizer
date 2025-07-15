package dev.tekofx.artganizer.ui.screens.tags

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.edit
import artganizer.app.generated.resources.tag_filled
import artganizer.app.generated.resources.trash
import dev.tekofx.artganizer.entities.TagWithSubmissions
import dev.tekofx.artganizer.getActivityViewModel
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.submission.GalleryRow
import dev.tekofx.artganizer.ui.components.tags.TagForm
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.toTagWithSubmissions
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagDetailsScreen(
    tagId: Long,
    navHostController: NavHostController,
    tagsViewModel: TagsViewModel = getActivityViewModel<TagsViewModel>(),
) {
    val showPopup by tagsViewModel.showPopup.collectAsState()
    val showTagEdit by tagsViewModel.showTagEdit.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        tagsViewModel.getTagById(tagId)
    }

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
                        "${NavigateDestinations.SUBMISSIONS_LIST}/$submissionId"
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
                    painterResource(Res.drawable.tag_filled),
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
                    icon = Res.drawable.edit,
                )
                ButtonWithIcon(
                    onClick = onDeleteClick,
                    text = "Delete",
                    icon = Res.drawable.trash,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        tagWithSubmissions.submissions.chunked(3).forEach { chunk ->
            item {
                GalleryRow(
                    submissions = chunk,
                    onImageClick = onImageClick
                )
            }
        }
    }
}
