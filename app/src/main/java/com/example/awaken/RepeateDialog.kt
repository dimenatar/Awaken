package com.example.awaken

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import com.example.awaken.alarm.WeekDays

class RepeateDialog(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener) : Dialog(context, cancelable, cancelListener)
{

    lateinit var linearLayout : LinearLayout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout = findViewById<LinearLayout>(R.id.dialog_days_of_week);
        //setContentView(R.layout.dialog_repeat);

    }

    public fun getCheckedWeekDays() : List<WeekDays>
    {
        val  selectedDays = mutableListOf<WeekDays>()

        val childCount = linearLayout.childCount;

        for (i in 0 until childCount)
        {
            if (linearLayout.getChildAt(i) is CheckBox)
            {
                val checkBoxItem = linearLayout.getChildAt(i) as CheckBox
                if (checkBoxItem.isChecked)
                {
                    selectedDays.add(WeekDays.fromInt(i - 1));
                }
            }
        }
        return selectedDays;
    }
}


