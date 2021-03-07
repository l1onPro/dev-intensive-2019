package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.shortFormat(): String {
    val pattern = if (this.isSameDay(Date())) "HH:mm" else "dd.MM.yy"
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

private fun Date.isSameDay(date:Date): Boolean {
    val day1 = this.time/ DAY
    val day2 = date.time/ DAY
    return day1 == day2
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits{
    SECOND {
        override fun plural(value: Int): String {
            return when(value % 100) {
                1, 21, 31, 41, 51, 61, 71, 81, 91 -> "$value секунду"
                in 2..4, in 22..24, in 32..34, in 42..44, in 52..54, in 62..64, in 72..74, in 82..84, in 92..94 -> "$value секунды"
                else -> "$value секунд"
            }
        }
    },
    MINUTE{
        override fun plural(value: Int): String {
            return when(value % 100) {
                1, 21, 31, 41, 51, 61, 71, 81, 91 -> "$value минуту"
                in 2..4, in 22..24, in 32..34, in 42..44, in 52..54, in 62..64, in 72..74, in 82..84, in 92..94 -> "$value минуты"
                else -> "$value минут"
            }
        }
    },
    HOUR{
        override fun plural(value: Int): String {
            return when(value % 100) {
                1, 21, 31, 41, 51, 61, 71, 81, 91 -> "$value час"
                in 2..4, in 22..24, in 32..34, in 42..44, in 52..54, in 62..64, in 72..74, in 82..84, in 92..94 -> "$value часа"
                else -> "$value часов"
            }
        }
    },
    DAY{
        override fun plural(value: Int): String {
            return when(value % 100) {
                1, 21, 31, 41, 51, 61, 71, 81, 91 -> "$value день"
                in 2..4, in 22..24, in 32..34, in 42..44, in 52..54, in 62..64, in 72..74, in 82..84, in 92..94 -> "$value дня"
                else -> "$value дней"
            }
        }
    };

    abstract fun plural(value: Int) : String
}

fun Date.humanizeDiff(date:Date = Date()) : String {

    val differenceTime = this.time - date.time
    val absDiffTime = abs(differenceTime)

    return when (abs(differenceTime)) {
        in 0..1 * SECOND -> if (differenceTime >= 0) "через мгновение" else "только что"
        in 1 * SECOND..45 * SECOND -> if (differenceTime > 0) "через несколько секунд" else "несколько секунд назад"
        in 45 * SECOND..47 * SECOND -> if (differenceTime > 0) "через минуту" else "минуту назад"
        in 75 * SECOND..45 * MINUTE -> "${if (differenceTime > 0) "через " else ""}${TimeUnits.MINUTE.plural((absDiffTime/ MINUTE).toInt())}${if (differenceTime < 0) " назад" else ""}"
        in 45 * MINUTE..75 * MINUTE -> if (differenceTime > 0) "через час" else "час назад"
        in 75 * MINUTE..22 * HOUR -> "${if (differenceTime > 0) "через " else ""}${TimeUnits.HOUR.plural((absDiffTime/ HOUR).toInt())}${if (differenceTime < 0) " назад" else ""}"
        in 22 * HOUR..26 * HOUR -> if (differenceTime > 0) "через день" else "день назад"
        in 26 * HOUR..360 * DAY -> "${if (differenceTime > 0) "через " else ""}${TimeUnits.DAY.plural((absDiffTime/ DAY).toInt())}${if (differenceTime < 0) " назад" else ""}"
        else -> if (differenceTime > 0) "более чем через год" else "более года назад"
    }
}