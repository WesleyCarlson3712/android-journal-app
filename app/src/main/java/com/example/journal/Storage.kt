package com.example.journal

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Saves all journal entries to internal storage as JSON.
 */
fun saveEntries(
    entries: List<Entry>,
    context: Context
) {

    val jsonString = Json.encodeToString(entries)

    context.openFileOutput(
        "entries.json",
        Context.MODE_PRIVATE
    ).use {

        it.write(jsonString.toByteArray())
    }
}

/**
 * Loads journal entries from internal storage.
 *
 * Returns an empty list if no save file exists.
 */
fun loadEntries(
    context: Context
): List<Entry> {

    return try {

        val jsonString = context.openFileInput(
            "entries.json"
        ).bufferedReader().use {

            it.readText()
        }

        Json.decodeFromString(jsonString)

    } catch (e: Exception) {

        emptyList()
    }
}

/**
 * Saves the current dark/light theme preference.
 */
fun saveTheme(
    darkTheme: Boolean,
    context: Context
) {

    context.openFileOutput(
        "theme.txt",
        Context.MODE_PRIVATE
    ).use {

        it.write(
            darkTheme.toString().toByteArray()
        )
    }
}

/**
 * Loads the user's saved theme preference.
 *
 * Defaults to dark mode if no file exists.
 */
fun loadTheme(
    context: Context
): Boolean {

    return try {

        context.openFileInput(
            "theme.txt"
        ).bufferedReader().use {

            it.readText().toBoolean()
        }

    } catch (e: Exception) {

        true
    }
}