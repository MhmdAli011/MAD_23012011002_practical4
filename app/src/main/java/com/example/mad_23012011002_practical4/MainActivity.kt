package com.example.mad_23012011002_practical4

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    lateinit var cardListAlarm: MaterialCardView
    lateinit var btnCreateAlarm: MaterialButton
    lateinit var btnCancelAlarm: MaterialButton
    lateinit var textAlaramTime: TextView

    private val REQUEST_NOTIFICATION_PERMISSION = 2001
    private val PENDING_INTENT_REQUEST_CODE = 234324243

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cardListAlarm = findViewById(R.id.card2)
        btnCreateAlarm = findViewById(R.id.create_alarm)
        btnCancelAlarm = findViewById(R.id.cancel_button)
        textAlaramTime = findViewById(R.id.datetime)
        cardListAlarm.visibility = View.GONE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }

        btnCreateAlarm.setOnClickListener { showTimeDialog() }
        btnCancelAlarm.setOnClickListener {
            cancelAlarm()
            cardListAlarm.visibility = View.GONE
        }
    }

    private fun showTimeDialog() {
        val cldr = Calendar.getInstance()
        val hour = cldr.get(Calendar.HOUR_OF_DAY)
        val minutes = cldr.get(Calendar.MINUTE)

        val picker = TimePickerDialog(
            this,
            { _, sHour, sMinute -> sendDialogDateToActivity(sHour, sMinute) },
            hour,
            minutes,
            false
        )
        picker.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun sendDialogDateToActivity(hour: Int, minute: Int) {
        val alarmCalendar = Calendar.getInstance()
        val year = alarmCalendar.get(Calendar.YEAR)
        val month = alarmCalendar.get(Calendar.MONTH)
        val day = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year, month, day, hour, minute, 0)

        if (alarmCalendar.timeInMillis <= System.currentTimeMillis()) {
            alarmCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        textAlaramTime.text =
            SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(alarmCalendar.time)
        cardListAlarm.visibility = View.VISIBLE

        setAlarm(alarmCalendar.timeInMillis, "Start")
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setAlarm(millisTime: Long, str: String) {
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1", str)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            PENDING_INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        if (str == "Start") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    Toast.makeText(
                        this,
                        "Please allow scheduling exact alarms in settings.",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                    return
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    millisTime,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, millisTime, pendingIntent)
            }

            Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show()
        } else if (str == "Stop") {
            alarmManager.cancel(pendingIntent)
            val stopIntent = Intent(this, AlarmBroadcastReceiver::class.java)
            stopIntent.putExtra("Service1", "Stop")
            sendBroadcast(stopIntent)
            Toast.makeText(this, "Alarm stopped", Toast.LENGTH_LONG).show()
        }
    }

    private fun cancelAlarm() {
        setAlarm(System.currentTimeMillis(), "Stop")
    }
}
