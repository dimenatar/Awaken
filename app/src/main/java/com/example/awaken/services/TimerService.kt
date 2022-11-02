package com.example.awaken.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class TimerService() : Service()
{
    override fun onCreate() {
        super.onCreate()
        Log.e("Awaken", "Create")
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.e("Awaken", "Bind")
        return null;
    }
}