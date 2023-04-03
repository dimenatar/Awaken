package com.example.awaken.alarm

enum class WeekDays
{
    None,
    Mon,
    Tue,
    Wed,
    Thu,
    Fri,
    Sat,
    Sun;

    companion object{
        public fun fromInt(value: Int) : WeekDays{
            return WeekDays.values().first() {it -> it.ordinal == value};
        }
    }
}