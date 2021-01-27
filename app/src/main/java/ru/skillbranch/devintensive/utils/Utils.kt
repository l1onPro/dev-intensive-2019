package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?>{
        val parts : List<String?>? = fullName?.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        if (firstName != null && firstName.isEmpty()){
            firstName = null
        }

        if (lastName != null && lastName.isEmpty()){
            lastName = null
        }

        return firstName to lastName
    }

    fun transliteration(payLoad: String, divider: String = " ") : String {

        val parts : List<String?>? = payLoad.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        val newFirstName = firstName?.replaceAll(transliteration)
        val newLastName = lastName?.replaceAll(transliteration)

        return "${newFirstName?.take(1)?.toUpperCase(Locale.ROOT)}${newFirstName?.drop(1)}$divider" +
                "${newLastName?.take(1)?.toUpperCase(Locale.ROOT)}${newLastName?.drop(1)}"
    }

    private fun String.replaceAll(keys: Map<String, String>) : String =
        keys.entries.fold(this) { acc, (key, value) -> acc.replace(key, value, ignoreCase = true)}


    private val transliteration = mapOf(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya"
    )

    fun toInitials(firstName: String?, lastName: String?): String? {

        val firstSymbol = firstName?.getOrNull(0)
        val secondSymbol = lastName?.getOrNull(1)

        if (firstName == null && lastName == null)
            return null

        if (firstName != null && firstName.isEmpty() && lastName != null && lastName.isEmpty()) {
            return null
        }

        val initials = "${firstSymbol ?: ""}${secondSymbol ?: ""}"

        return initials.toUpperCase(Locale.ROOT)
    }
}