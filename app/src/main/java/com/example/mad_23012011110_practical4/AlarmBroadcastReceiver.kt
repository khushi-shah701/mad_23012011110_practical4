package com.example.mad_23012011110_practical4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val str1 = intent.getStringExtra("Service1")
        if(str1 != null){
            val intentService = Intent(context, AlarmService::class.java)
            if (str1 == "Start")
                context.startService(intentService)
            else if (str1 == "Stop")
                context.stopService(intentService)
        }
    }
}