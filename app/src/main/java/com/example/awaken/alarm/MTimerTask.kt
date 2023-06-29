package com.example.awaken.alarm

import android.os.CountDownTimer

class MTimerTask(millisInFuture: Long, countDownInterval: Long, var id: Int, onFinish: () -> Unit, onTick: (() -> Unit)? = null) : CountDownTimer(millisInFuture, countDownInterval)
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