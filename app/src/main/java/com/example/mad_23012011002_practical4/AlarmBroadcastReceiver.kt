package com.example.mad_23012011002_practical4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.getStringExtra("Service1")

        if (action == "Start" || action == "Stop") {
            val serviceIntent = Intent(context, AlarmServices::class.java)
            serviceIntent.putExtra("Service1", action)

            if (action == "Start") {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ContextCompat.startForegroundService(context, serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
            } else {
                context.stopService(serviceIntent)
            }
        }
    }
}
