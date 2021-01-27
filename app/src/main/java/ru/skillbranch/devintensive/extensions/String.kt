package ru.skillbranch.devintensive.extensions

const val TREE_DOTS = "..."

fun String.truncate(maxSymbol: Int = 16) : String {
    val text = this.trimEnd().take(maxSymbol)
    return if (this.trimEnd().length > maxSymbol) "${text.trim()}..." else text.trim()
}

fun String.stripHtml() : String {
    return this
        .replace(regex = Regex("<[^>]+>"), replacement = "")
        .replace(regex = Regex("[&<>'\"]"), replacement = "")
        .replace(regex = "\\s+".toRegex(), replacement = " ")
}