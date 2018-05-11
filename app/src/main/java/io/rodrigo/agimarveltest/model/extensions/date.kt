package io.rodrigo.agimarveltest.model.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.dateString(format: String? = null, style: Int? = null): String {
    val formatter = if (format == null) {
        DateFormat.getDateInstance(style ?: java.text.DateFormat.SHORT)
    } else {
        SimpleDateFormat(format, Locale.getDefault())
    }
    return formatter.format(this)
}