package com.example.awaken.model

import com.example.awaken.alarm.Time
import com.example.awaken.alarm.WeekDays
import java.io.Serializable

data class Alarm(var time: Time, var days: List<WeekDays>, var id: Int = 0) : Serializable
{
    constructor(time: Time, days: String, id: Int = 0) : this(time, getDaysFromString(days), id)

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

