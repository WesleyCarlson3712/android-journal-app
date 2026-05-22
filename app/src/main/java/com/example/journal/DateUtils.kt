package com.example.journal

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Returns the current system time in milliseconds.
 */
fun getCurrentTime(): Long {

    return System.currentTimeMillis()
}

/**
 * Converts a timestamp into a readable date string.
 *
 * Example:
 * May 21, 2026
 */
fun formatDate(
    time: Long
): String {

    val formatter = SimpleDateFormat(
        "MMMM d, yyyy",
        Locale.getDefault()
    )

    return formatter.format(Date(time))
}

/**
 * Returns true if two timestamps occur on different calendar days.
 *
 * Used to determine whether an edit date should be shown.
 */
fun isDifferentDay(
    firstTime: Long,
    secondTime: Long
): Boolean {

    val formatter = SimpleDateFormat(
        "yyyyMMdd",
        Locale.getDefault()
    )

    return formatter.format(Date(firstTime)) !=
            formatter.format(Date(secondTime))
}