import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.journal.Entry
import com.example.journal.formatDate
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.text.style.TextOverflow

/**
 * Main journal screen.
 *
 * Displays:
 * - list of entries
 * - create entry button
 * - theme toggle button
 */
@Composable
fun JournalScreen(
    entries: MutableList<Entry>,
    onEntrySelected: (Entry) -> Unit,
    onNewEntryPressed: () -> Unit,
    onThemeToggle: () -> Unit,
    darkTheme: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    onNewEntryPressed()
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Entry"
                )
            }

            Text(
                text = "Journal",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium
            )

            Button(
                onClick = {
                    onThemeToggle()
                }
            ) {

                Icon(
                    imageVector =
                        if (darkTheme) {
                            Icons.Default.LightMode
                        } else {
                            Icons.Default.DarkMode
                        },

                    contentDescription = "Toggle Theme",

                    tint =
                        if (darkTheme) {
                            androidx.compose.ui.graphics.Color.White
                        } else {
                            androidx.compose.ui.graphics.Color.Black
                        }
                )
            }
        }

        LazyColumn {

            items(entries.reversed()) { entry ->

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    onClick = {
                        onEntrySelected(entry)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                Text(
                                    text = "Created: ${formatDate(entry.creationTime)}",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                                )

                                if (entry.lastEditedTime != null) {

                                    Text(
                                        text = "Edited: ${formatDate(entry.lastEditedTime!!)}",
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            Text(
                                text = entry.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}