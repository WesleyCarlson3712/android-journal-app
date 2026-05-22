import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.journal.Entry
import com.example.journal.getCurrentTime
import com.example.journal.loadEntries
import com.example.journal.loadTheme
import com.example.journal.saveEntries
import com.example.journal.saveTheme
import com.example.journal.ui.theme.JournalTheme

/**
 * Main application controller.
 *
 * Handles:
 * - screen navigation
 * - selected entry state
 * - theme state
 * - loading and saving data
 */


@Composable
fun JournalApp() {

    val context = LocalContext.current

    val entries = remember {

        mutableStateListOf<Entry>().apply {

            addAll(loadEntries(context))
        }
    }

    var selectedEntry by remember {
        mutableStateOf<Entry?>(null)
    }

    var creatingNewEntry by remember {
        mutableStateOf(false)
    }
    var darkTheme by remember {
        mutableStateOf(
            loadTheme(context)
        )
    }
    JournalTheme(
        darkTheme = darkTheme
    ) {
        if (selectedEntry == null) {
            // if we have not selected an entry, show the journal screen
            JournalScreen(
                entries = entries,

                onEntrySelected = { entry ->
                    selectedEntry = entry
                },

                onNewEntryPressed = {

                    creatingNewEntry = true

                    selectedEntry = Entry(
                        title = "",
                        content = "",
                        creationTime = getCurrentTime(),
                        lastEditedTime = null
                    )
                },
                onThemeToggle = {

                    darkTheme = !darkTheme

                    saveTheme(
                        darkTheme,
                        context
                    )
                }
            )
        } else {
            // if we have selected an entry, go to the entry display screen
            EntryScreen(
                entry = selectedEntry!!,
                isExistingEntry = !creatingNewEntry,
                onDonePressed = {

                    if (creatingNewEntry) {

                        entries.add(selectedEntry!!)
                    }

                    saveEntries(entries, context)

                    creatingNewEntry = false

                    selectedEntry = null
                },

                onCancelPressed = {

                    creatingNewEntry = false

                    selectedEntry = null
                },

                onDeletePressed = {

                    entries.remove(selectedEntry)

                    saveEntries(entries, context)

                    selectedEntry = null
                }
            )
        }
    }
}
