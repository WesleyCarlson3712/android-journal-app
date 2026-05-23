import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.journal.Entry
import com.example.journal.formatDate
import com.example.journal.getCurrentTime
import com.example.journal.isDifferentDay
/**
 * Screen used to create or edit a journal entry.
 *
 * If the entry already exists:
 * - delete button is shown
 * - edits may update the last edited date
 *
 * If creating a new entry:
 * - delete button is hidden
 * - cancel simply discards the entry
 */
@Composable
fun EntryScreen(
    entry: Entry,
    isExistingEntry: Boolean,
    onDonePressed: () -> Unit,
    onDeletePressed: () -> Unit,
    onCancelPressed: () -> Unit
) {

    var currentTitle by remember {
        mutableStateOf(entry.title)
    }

    var currentText by remember {
        mutableStateOf(entry.content)
    }

    val canSave =
        currentTitle.isNotBlank() && currentText.isNotBlank() &&
                (currentTitle != entry.title || currentText != entry.content)

    BackHandler {

        onCancelPressed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(16.dp)
    ) {

        Text(
            text = formatDate(entry.creationTime),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        TextField(
            value = currentTitle,

            onValueChange = {
                currentTitle = it
            },

            label = {
                Text("Title")
            },
            maxLines = 3,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),

            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = currentText,

            onValueChange = {
                currentText = it
            },

            label = {
                Text("Entry")
            },

            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),

            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {
                    onCancelPressed()
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel"
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    enabled = canSave,

                    onClick = {

                        if (
                            isExistingEntry &&
                            (entry.content != currentText || entry.title != currentTitle) &&
                            isDifferentDay(
                                entry.creationTime,
                                getCurrentTime()
                            )
                        ) {
                            entry.lastEditedTime = getCurrentTime()
                        }
                        entry.content = currentText
                        entry.title = currentTitle


                        onDonePressed()
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done"
                    )
                }
            }
            if (isExistingEntry) {
                Button(
                    onClick = {
                        onDeletePressed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    }
}
