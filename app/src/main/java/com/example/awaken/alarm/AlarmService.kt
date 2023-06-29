package com.example.awaken.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.awaken.MainActivity
import com.example.awaken.R
import com.example.awaken.model.Alarm
import com.example.awaken.services.Restarter
import java.util.*


class AlarmService () : Service()
{
    lateinit var _context : Context
    private val binder = AlarmBinder();
    private var alarmCount = 0;
    private val tasks = mutableListOf<MTimerTask>()

    constructor(context : Context) : this()
    {
        _context = context
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground()
        else
            startForeground(1, Notification())

        Log.d("Awaken", "Create")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyOwnForeground() {
        val NOTIFICATION_CHANNEL_ID = "example.permanence"
        val channelName = "Background Service"
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
        _context = applicationContext
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder?
    {
        return binder;
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun GetMilisFromCalendar(calendar: Calendar) : Long
    {
        return Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)).GetTimeInMilis();
    }

    fun FireAlarm(alarm: Alarm)
    {
        Toast.makeText(_context, "Alarm ${alarm.id} is firing!", Toast.LENGTH_LONG).show()
        MainActivity.ShowNotification(_context, 1, "Awakennotifications", "ЭЭээээ", "Вставай блять курво", R.mipmap.ic_launcher)
    }

    fun addAlarm(alarm: Alarm)
    {
        val id: Int = if (tasks.size > 0) {
            tasks.maxOf { it.id } + 1;
        } else 0;
        alarm.id = id;
        val mTimerTask = MTimerTask(alarm.time.GetTimeInMilis() - GetMilisFromCalendar(Calendar.getInstance()), 1000, id, {FireAlarm(alarm)})
        tasks.add(mTimerTask);
        mTimerTask.start()
    }

    fun removeAlarm(alarm: Alarm)
    {
        val task = tasks.first{ it.id == alarm.id }
        task.cancel()
        tasks.remove(task);
    }

    fun removeLastAlarm()
    {
        val task = tasks.last();
        task.cancel()
        tasks.remove(task)
    }
}

