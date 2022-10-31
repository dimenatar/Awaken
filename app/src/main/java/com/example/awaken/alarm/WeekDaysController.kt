package com.example.awaken.alarm

class WeekDaysController
{
    private var settedWeekDays : MutableList<WeekDays> = getWorkDays().toMutableList()

    public val SettedWeekDays : List<WeekDays>
    get() = settedWeekDays

    public fun setWorkDays()
    {
        settedWeekDays = getWorkDays().toMutableList()
    }

    public fun setAllDays()
    {
        settedWeekDays = getAllDays().toMutableList()
    }


    public fun addWorkDays(weekDays: WeekDays) = settedWeekDays.add(weekDays)
    public fun addWorkDays(weekDays: List<WeekDays>) = settedWeekDays.addAll(weekDays)


    private fun getWorkDays() : List<WeekDays> = listOf(WeekDays.Mon, WeekDays.Tue, WeekDays.Wed, WeekDays.Thu, WeekDays.Fri)
    private fun getAllDays() : List<WeekDays> = enumValues<WeekDays>().toList()
}