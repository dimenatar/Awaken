package com.example.awaken.model

import com.example.awaken.alarm.Time
import com.example.awaken.alarm.WeekDays

data class Alarm(var time: Time, var days: List<WeekDays>)
{
    constructor(time: Time, days: String) : this(time, days.split(", ").map { it -> WeekDays.valueOf(it)})


    fun getDays() : String
    {
        return days.joinToString { it -> "$it, " }
    }


}

