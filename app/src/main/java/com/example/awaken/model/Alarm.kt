package com.example.awaken.model

import com.example.awaken.alarm.Time
import com.example.awaken.alarm.WeekDays
import java.io.Serializable

data class Alarm(var time: Time, var days: List<WeekDays>) : Serializable
{
    constructor(time: Time, days: String) : this(time, getDaysFromString(days))

    var isActive = true
    var id = 0

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

