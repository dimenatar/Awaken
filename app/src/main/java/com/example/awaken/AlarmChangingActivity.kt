package com.example.awaken

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.awaken.alarm.Time
import com.example.awaken.model.Alarm

class AlarmChangingActivity : AlarmSettingsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedIntent = intent;
        val alarm = receivedIntent.getSerializableExtra("Alarm") as Alarm

        timePicker.hour = alarm.time.Hours.toInt()
        timePicker.minute = alarm.time.Minutes.toInt()

        setButton.setOnClickListener {
            alarm.time = Time(timePicker.hour, timePicker.minute)
            alarm.days = days;
            dbHelper.updateAlarm(alarm)
        }
    }
}