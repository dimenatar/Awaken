package com.example.awaken

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.awaken.alarm.AlarmBinder
import com.example.awaken.alarm.AlarmService
import com.example.awaken.alarm.Time
import com.example.awaken.model.Alarm
import com.example.awaken.model.DBHelper
import com.example.awaken.services.Restarter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity()
{
    private lateinit var timePicker : TimePicker
    private lateinit var setButton : Button
    private lateinit var removeButton : Button
    private lateinit var createAlarmButton : FloatingActionButton
    private lateinit var serviceIntent : Intent
    private lateinit var recyclerView : RecyclerView

    private lateinit var alarmService : AlarmService;
    private var serviceID = 0;

    private val services = mutableListOf<AlarmService>()

    companion object
    {
        fun ShowNotification(context: Context, ID : Int, channelID : String, contentTitle : String, contentText : String, iconID : Int)
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
        createAlarmButton = findViewById(R.id.create_alarm)
        recyclerView = findViewById(R.id.alarm_list)
        removeButton = findViewById(R.id.remove_button)
        createNotificationChannel()

        alarmService = AlarmService(this)
        this.serviceIntent = Intent(this, alarmService.javaClass)

        this.serviceIntent.putExtra("SettedTime", Time(timePicker.hour, timePicker.minute))
        this.serviceIntent.putExtra("Vibrate", false)
        this.serviceIntent.putExtra("PathToMusic", "")

        if (!isMyServiceRunning(this.serviceIntent.javaClass))
        {

            startService(this.serviceIntent)
        }
        else
        {
            val connection = object:ServiceConnection
            {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    val binder = service as AlarmBinder;
                    alarmService = binder.getService();
                }

                override fun onServiceDisconnected(p0: ComponentName?) {

                }
            }
        }


        setButton.setOnClickListener{


        }

        removeButton.setOnClickListener {
            alarmService.removeLastAlarm()
        }

        createAlarmButton.setOnClickListener{
            //val intent = Intent(this, AlarmCreationActivity::class.java)
            //startActivity(intent);

            val alarm = Alarm(Time(timePicker.hour, timePicker.minute), listOf())
            alarmService.addAlarm(alarm)
        }

        val dbHelper = DBHelper(this);
        val alarms = dbHelper.getAlarmsFromDatabase()
        val alarmAdapter = AlarmAdapter(this, alarms)
        recyclerView.adapter = alarmAdapter;

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

    override fun onResume() {
        super.onResume()

    }
}