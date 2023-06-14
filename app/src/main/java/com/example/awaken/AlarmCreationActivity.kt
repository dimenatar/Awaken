package com.example.awaken

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.awaken.alarm.Time
import com.example.awaken.model.Alarm

class AlarmCreationActivity : AlarmSettingsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton.setOnClickListener{

            dbHelper.insertAlarm(createAlarm())
        }
    }
}