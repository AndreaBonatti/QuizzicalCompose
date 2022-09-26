package com.example.quizzicalcompose.util

import androidx.core.text.HtmlCompat

fun htmlToString(string: String): String {
    return HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}