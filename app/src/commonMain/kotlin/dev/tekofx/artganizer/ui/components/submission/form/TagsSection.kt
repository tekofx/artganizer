package dev.tekofx.artganizer.ui.components.submission.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.tag_filled
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.tags.SmallTagCard
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun TagsSection(
    submissionDetails: SubmissionDetails,
    onAddTagsClick: () -> Unit,
    onValueChange: (SubmissionDetails) -> Unit
) {
    Text("Tags", style = MaterialTheme.typography.headlineSmall)
    if (submissionDetails.tags.isEmpty()) {
        ButtonWithIcon(
            onClick = onAddTagsClick,
            icon = Res.drawable.tag_filled,
            text = "Add Tags",
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(submissionDetails.tags) { tag ->
                SmallTagCard(
                    tag,
                    deletable = true,
                    onClear = {
                        onValueChange(
                            submissionDetails.copy(
                                tags = submissionDetails.tags.filterNot { it.tagId == tag.tagId }
                            )
                        )
                    }
                )
            }
            item {
                ButtonWithIcon(
                    onClick = onAddTagsClick,
                    icon = Res.drawable.tag_filled,
                    text = "Add Tags",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = MaterialTheme.shapes.medium
                )
            }
        }
    }
}

@Composable
@Preview
fun TagsInputPreview() {
    Column {

        TagsSection(
            submissionDetails = SubmissionDetails(
                title = "Sample Submission",
                description = "This is a sample submission description.",
                rating = 5,
                tags = listOf(Tag(tagId = 1, name = "Sample Tag"))
            ),
            onAddTagsClick = {},
            onValueChange = {}
        )
    }
}