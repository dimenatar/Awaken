package com.example.awaken

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AlarmFiringActivity : AppCompatActivity() {
    private lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_firing)

        stopButton = findViewById(R.id.alarm_firing_stop)
    }
}