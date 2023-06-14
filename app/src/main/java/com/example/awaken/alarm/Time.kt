package com.example.awaken.alarm

import android.icu.util.Calendar
import java.io.Serializable
import java.util.concurrent.TimeUnit

class Time (hours: Int, minutes : Int, seconds : Int = 0) : Serializable
{
    private var hours = hours.toLong()
    private var minutes = minutes.toLong()
    private var seconds = seconds.toLong();

    val Hours : Long get() { return this.hours }
    val Minutes : Long get() { return this.minutes }
    val Seconds : Long get() { return this.seconds}

    fun GetTimeInSeconds() : Long
    {
        return hours * 3600 + minutes * 60 + seconds;
    }

    fun GetTimeInMilis() : Long = GetTimeInSeconds() * 1000;
}