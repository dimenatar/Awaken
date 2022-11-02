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
import com.example.awaken.services.Restarter
import java.util.*


class Alarm () : Service()
{
    class MTimerTask(millisInFuture: Long, countDownInterval: Long, onFinish: () -> Unit, onTick: (() -> Unit)? = null) : CountDownTimer(millisInFuture, countDownInterval)
    {
        private var onFinishAction  = onFinish
        private var onTickAction = onTick

        override fun onTick(p0: Long)
        {
            if (onTickAction != null)
            {
                onTickAction?.let { it() }
            }
        }

        override fun onFinish()
        {
            onFinishAction();
        }

    }

    public lateinit var _settedTime : Time
    public lateinit var _pathToMusic : String
    public var _vibrate : Boolean = false
    public lateinit var _context : Context
    public val weekDaysController = WeekDaysController()

    private var calendar = Calendar.getInstance()

    private var timeLeft : Long = 0L

    private lateinit var timerTask : CountDownTimer
    constructor(settedTime: Time, pathToMusic : String, vibrate : Boolean, context : Context) : this()
    {
        _settedTime = settedTime;
        _pathToMusic = pathToMusic
        _vibrate = vibrate
        _context = context
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) startMyOwnForeground() else startForeground(
            1,
            Notification()
        )

        Log.d("Awaken", "Create")
        //SetupTimer()
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
        _settedTime =  intent!!.getSerializableExtra("SettedTime") as Time
        _pathToMusic = intent.getStringExtra("PathToMusic")!!
        _vibrate = intent.getBooleanExtra("Vibrate", false)
        //_context = intent.getSerializableExtra("Context") as Context
        _context = applicationContext

        Log.d("Awaken", "OnBind")

        SetupTimer()

        //return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder?
    {


        return null
    }


    override fun onDestroy() {
        super.onDestroy()
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, Restarter::class.java)
        this.sendBroadcast(broadcastIntent)
    }

    private fun GetMilisFromCalendar(calendar: Calendar) : Long
    {
        return Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)).GetTimeInMilis();
    }

    private fun SetupTimer()
    {
        timeLeft = ((_settedTime.GetTimeInMilis() - GetMilisFromCalendar(calendar))/1000)

        Toast.makeText(_context, "Alarm will be fired in $timeLeft seconds! ", Toast.LENGTH_LONG).show()

        timerTask = MTimerTask(timeLeft * 1000,1000, {FireAlarm()}) //{ Toast.makeText(context, "Left: ${timeLeft--}", Toast.LENGTH_SHORT).show()})
        timerTask.start()
    }

    public fun Update()
    {
        timerTask.cancel()
    }

    public fun FireAlarm()
    {
        Toast.makeText(_context, "Alarm is firing!", Toast.LENGTH_LONG).show()
        //CreateNotification(_context)
        MainActivity.ShowNotification(_context, 1, "Awakennotifications", "ЭЭээээ", "Вставай блять курво", R.mipmap.ic_launcher)
    }

    private fun CreateNotification(context: Context)
    {
        val builder = NotificationCompat.Builder(context, "Awakennotifications")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Напоминание")
            .setContentText("Пора покормить кота")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        val notificationManager : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;

        notificationManager.notify(1, builder.build())
    }



}

