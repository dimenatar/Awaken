package com.example.awaken.alarm

import android.os.Binder

class AlarmBinder : Binder() {
    fun getService() : AlarmService
    {
        return AlarmService()
    }
}