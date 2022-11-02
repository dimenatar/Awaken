package com.example.awaken

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.awaken.alarm.Alarm
import com.example.awaken.alarm.Time
import com.example.awaken.services.Restarter


class MainActivity : AppCompatActivity()
{
    private lateinit var timePicker : TimePicker
    private lateinit var setButton : Button

    private lateinit var alarmService : Intent

    companion object
    {
        public fun ShowNotification(context: Context, ID : Int, channelID : String, contentTitle : String, contentText : String, iconID : Int)
        {
            val builder = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(iconID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            val notificationManager : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;

            notificationManager.notify(ID, builder.build())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timePicker = findViewById<TimePicker>( R.id.time_picker)
        setButton = findViewById<Button>(R.id.set_button)

        createNotificationChannel()

        setButton.setOnClickListener{
            var alarm = Alarm(Time(timePicker.hour, timePicker.minute), "", true, this )

            alarmService = Intent(this, alarm.javaClass)

            alarmService.putExtra("SettedTime", Time(timePicker.hour, timePicker.minute))
            alarmService.putExtra("Vibrate", false)
            alarmService.putExtra("PathToMusic", "")

            if (!isMyServiceRunning(alarmService.javaClass))
            {
                startService(alarmService)
            }

        }


    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Awaken"
            val descriptionText = "Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Awakennotifications", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("Service status", "Running")
                return true
            }
        }
        Log.i("Service status", "Not running")
        return false
    }
    override fun onDestroy() {
        //stopService(mServiceIntent);
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, Restarter::class.java)
        this.sendBroadcast(broadcastIntent)
        super.onDestroy()
    }
}