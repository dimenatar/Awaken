package com.example.awaken.alarm

import android.app.NotificationManager
import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.awaken.MainActivity
import java.util.*
import com.example.awaken.R

class Alarm (settedTime: Time, pathToMusic : String, vibrate : Boolean, context : Context)
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

    public var _settedTime = settedTime
    public var _pathToMusic = pathToMusic
    public var _vibrate = vibrate
    public var _context = context
    public val weekDaysController = WeekDaysController()

    private var calendar = Calendar.getInstance()

    private var timeLeft : Long = 0L

    private var timerTask : CountDownTimer

    init
    {
        timeLeft = ((settedTime.GetTimeInMilis() - GetMilisFromCalendar(calendar))/1000)

        Toast.makeText(_context, "Alarm will be fired in $timeLeft seconds! ", Toast.LENGTH_LONG).show()

        timerTask = MTimerTask(timeLeft * 1000,1000, {FireAlarm()}) //{ Toast.makeText(context, "Left: ${timeLeft--}", Toast.LENGTH_SHORT).show()})
        timerTask.start()
        //CreateNotification(_context)
    }

    private fun GetMilisFromCalendar(calendar: Calendar) : Long
    {
        return Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)).GetTimeInMilis();
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

