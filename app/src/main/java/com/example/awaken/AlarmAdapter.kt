package com.example.awaken

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.awaken.model.Alarm
import com.google.android.material.slider.Slider

class AlarmAdapter(private val alarms: List<Alarm>) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>(), View.OnClickListener {

    class AlarmViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {

        val daysTextView: TextView = view.findViewById(R.id.alarm_item_days)
        val timeTextView: TextView = view.findViewById(R.id.alarm_item_time)
        val isActiveSwitch: SwitchCompat = view.findViewById(R.id.alarm_item_slider)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_item, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]

        holder.daysTextView.text = alarm.getDays()
        holder.timeTextView.text = "${alarm.time.Hours}:${alarm.time.Minutes}"
        holder.isActiveSwitch.isChecked = alarm.isActive
    }

    override fun getItemCount(): Int {
        return alarms.count()
    }
}