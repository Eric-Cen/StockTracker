package com.mcarving.stocktracker.Settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.preference.PreferenceManager
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.util.Log
import android.view.View
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.Schedules.AlarmJobService
import com.mcarving.stocktracker.Schedules.AlarmJobService.Companion.FIFTEEN_SEC_MILLIS
import com.mcarving.stocktracker.Schedules.AlarmJobService.Companion.REQUEST_CODE
import com.mcarving.stocktracker.Schedules.AlarmScheduler
import io.reactivex.internal.subscriptions.SubscriptionHelper.cancel
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.preferences)

        readFromPreference()
    }

    override fun onStop() {
        super.onStop()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onStart() {
        super.onStart()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key?.apply {
            if(this==getString(R.string.pref_notifications)){
                val toTurnAlarmOn = sharedPreferences?.getBoolean(
                    getString(R.string.pref_notifications), false) ?: false

                if(toTurnAlarmOn){
                    //turn on alarm
                    setAlarm()
                } else {
                    //turn off alarm
                    cancelAlarm()
                }
            }
        }
    }

    fun cancelAlarm(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AlarmScheduler.getInstance().cancelSchedule(preferenceManager.context)
        } else{
            val myIntent = Intent(preferenceManager.context, AlarmJobService::class.java)
            val pendingIntent = PendingIntent.getService(preferenceManager.context,
                REQUEST_CODE,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmManager = preferenceManager.context
                .getSystemService(Context.ALARM_SERVICE) as? AlarmManager

            alarmManager?.apply {
                cancel(pendingIntent)
            }
        }

        readFromPreference()
    }

    fun setAlarm(){
        val myIntent = Intent(preferenceManager.context, AlarmJobService::class.java)

        val pendingIntent = PendingIntent.getService(preferenceManager.context,
            REQUEST_CODE,
            myIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = preferenceManager.context
            .getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, 16)
//            set(Calendar.MINUTE, 25)
        }
        val time = SystemClock.elapsedRealtime()


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Log.d("what", "MainActivity: scheduling with jobscheduler")
            // USE JOBSCHEDULER
            AlarmScheduler.getInstance().schedule(preferenceManager.context)

        } else {
            //calendar.add(Calendar.SECOND, 10)
            alarmManager?.apply {
                set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis + FIFTEEN_SEC_MILLIS, pendingIntent)
//            setRepeating(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
//                FIFTEEN_SEC_MILLIS,
//                pendingIntent)
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                val formatted = formatter.format(calendar.time)
                val strValue = "alarm is set..ready on " + calendar.time
                //statusTextView.text = strValue
                //TODO setup value in the next alarm time preference
            }

        }

        readFromPreference()
    }

    fun readFromPreference(){
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(preferenceManager.context)
        val alarmStatus = preferences.getBoolean(AlarmScheduler.PREF_ALARM_STATUS, false)
        if(alarmStatus){
            //statusTextView.text="Alarm is scheduled."
        } else {
            val nextRunPreference = preferenceManager
                .findPreference(resources.getString(R.string.pref_key_next_run))
            nextRunPreference.setTitle("Next Run: Not Scheduled")
        }

        val lastStatus = preferences.getString(AlarmScheduler.PREF_LAST_SCHEDULE_RuN, "Not Available")
        lastStatus.apply {
            //lastTextView.text = this
            val lastRunPreference = preferenceManager
                .findPreference(resources.getString(R.string.pref_key_last_run))
            lastRunPreference.setTitle("Last Run: " + this)
        }

        val nextStatus = preferences.getString(AlarmScheduler.PREF_NEXT_SCHEDULE_RUN, "Not Available")
        nextStatus.apply {
            //nextTextView.text = this
            val nextRunPreference = preferenceManager
                .findPreference(resources.getString(R.string.pref_key_next_run))
            nextRunPreference.setTitle("Next Run: " + this)
        }

    }
}