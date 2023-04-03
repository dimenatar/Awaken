package com.example.awaken.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.awaken.alarm.AlarmService
import com.example.awaken.alarm.Time

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{

    companion object{
        const val DATABASE_NAME = "alarm.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "alarms"

        const val ID = "id"
        const val COL_HOURS = "hours"
        const val COL_MINUTES = "minutes"
        const val COL_DAYS = "days"

        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_HOURS INTEGER, $COL_MINUTES INTEGER, $COL_DAYS TEXT)"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?)
    {
        sqLiteDatabase?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, p1: Int, p2: Int)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun insertAlarm(alarm: Alarm) : Long
    {
        val database = this.writableDatabase
        val values = ContentValues()
        values.put(COL_HOURS, alarm.time.Hours)
        values.put(COL_MINUTES, alarm.time.Hours)
        values.put(COL_DAYS, alarm.getDays())

        val status = database.insert(TABLE_NAME, null, values);
        database.close()
        return status
    }

    @SuppressLint("Range")
    fun getAlarmsFromDatabase() : List<Alarm>
    {
        val database = this.readableDatabase
        val cursor = database.query(TABLE_NAME, null, null, null, null, null, null)

        val alarms = mutableListOf<Alarm>()
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                val time = Time(cursor.getInt(cursor.getColumnIndex(COL_HOURS)), cursor.getInt(cursor.getColumnIndex(COL_MINUTES)), 0)
                val days = cursor.getString(cursor.getColumnIndex(COL_DAYS))
                val alarm = Alarm(time, days)
                alarms.add(alarm)
            }
        }

        return alarms;
    }

    fun getLastInsertedId() : String
    {
        val database = this.readableDatabase;
        val cursor = database.rawQuery("SELECT $ID FROM $TABLE_NAME ORDER BY $ID DESC LIMIT 1", null)
        if (cursor != null && cursor.count > 0)
        {
            cursor.moveToNext()
            val columnIndex = cursor.getColumnIndex(ID)

            if (columnIndex >= 0)
            {
                return cursor.getString(columnIndex);
            }
        }
        return ""
    }

}