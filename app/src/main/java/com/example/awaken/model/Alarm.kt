package com.example.awaken.model

import com.example.awaken.alarm.Time
import com.example.awaken.alarm.WeekDays

data class Alarm(var time: Time, var days: List<WeekDays>)
{
    constructor(time: Time, days: String) : this(time, getDaysFromString(days))

    var isActive = true

    fun getDays() : String
    {
        return days.joinToString { it -> "$it" }
    }
companion object {
    fun getDaysFromString(days: String): List<WeekDays> {
        return days.split(", ").filter { it != "" }.map {
            WeekDays.valueOf(it)
        }
    }
}
}

