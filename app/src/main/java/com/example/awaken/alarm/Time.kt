package com.example.awaken.alarm

import android.icu.util.Calendar
import java.util.concurrent.TimeUnit

class Time (hours: Int, minutes : Int, seconds : Int = 0)
{
    private var hours = hours.toLong()
    private var minutes = minutes.toLong()
    private var seconds = seconds.toLong();

    public val Hours : Long get() { return this.hours }
    public val Minutes : Long get() { return this.minutes }
    public val Seconds : Long get() { return this.seconds}

    public fun GetTimeInSeconds() : Long
    {
        return hours * 3600 + minutes * 60 + seconds;
    }

    public fun GetTimeInMilis() : Long = GetTimeInSeconds() * 1000;
}