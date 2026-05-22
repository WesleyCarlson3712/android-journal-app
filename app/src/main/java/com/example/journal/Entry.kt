package com.example.journal

import kotlinx.serialization.Serializable

/**
 * Represents a single journal entry.
 *
 * title:
 * The title displayed in the journal list.
 *
 * content:
 * The body text of the journal entry.
 *
 * creationTime:
 * The time the entry was originally created.
 *
 * lastEditedTime:
 * The most recent edit time.
 * Null if the entry has never been edited.
 */
@Serializable
data class Entry(

    var title: String,

    var content: String,

    var creationTime: Long,

    var lastEditedTime: Long? = null
)