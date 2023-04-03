package com.example.awaken

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import com.example.awaken.alarm.WeekDays
import com.example.awaken.model.DBHelper

open class AlarmSettingsActivity : AppCompatActivity()
{
    protected lateinit var repeatText : TextView
    protected lateinit var setButton : Button
    protected lateinit var daysDialog : RepeateDialog
    protected lateinit var timePicker : TimePicker
    protected lateinit var dbHelper: DBHelper
    protected lateinit var days: MutableList<WeekDays>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_creation)

        dbHelper = DBHelper(this)
        repeatText = findViewById(R.id.alarm_settings_repeat)
        setButton = findViewById(R.id.set);
        timePicker = findViewById(R.id.timePicker)
        repeatText.setOnClickListener{
            daysDialog = RepeateDialog(this, true)
            {
                onDaysDialogClosed(daysDialog);
            }
            daysDialog.setContentView(R.layout.dialog_repeat)
            daysDialog.findViewById<Button>(R.id.dialog_repeat_ok).setOnClickListener{
                onDaysDialogClosed(daysDialog);
                daysDialog.dismiss()
            }
            daysDialog.setCanceledOnTouchOutside(true)
            daysDialog.show();
        }
    }

    protected fun onDaysDialogClosed(dialog: RepeateDialog)
    {
        days = dialog.getCheckedWeekDays().toMutableList()
        if (days.isEmpty())
        {
            days.add(WeekDays.None);
        }
    }

}