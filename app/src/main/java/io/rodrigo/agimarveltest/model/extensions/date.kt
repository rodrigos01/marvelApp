package io.rodrigo.agimarveltest.model.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.dateString(format: String? = null): String {
    val formatter = if (format == null) {
        java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT)
    } else {
        SimpleDateFormat(format, Locale.getDefault())
    }
    return formatter.format(this)
}