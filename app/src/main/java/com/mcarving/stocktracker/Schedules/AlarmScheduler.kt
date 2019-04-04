package com.mcarving.stocktracker.Schedules

import android.app.AlarmManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import android.support.annotation.RequiresApi
import android.util.Log
import java.util.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class AlarmScheduler {
    private val FIVE_MINUTES_MS = 5 * 60 * 1000L

    fun msToNextAlarm(): Long {
        val TAG = "AlarmScheduler"
        var nextAlarmInMS = 0L

        // how to set time on the current day
        val startTime = AppUtil.getStarttime()
        startTime.set(Calendar.HOUR_OF_DAY, 9)
        startTime.set(Calendar.MINUTE, 30)
        startTime.set(Calendar.SECOND, 0)
        startTime.set(Calendar.MILLISECOND, 0)

        val endTime = AppUtil.getEndTime()
        endTime.set(Calendar.HOUR_OF_DAY, 16)
        endTime.set(Calendar.MINUTE, 0)
        endTime.set(Calendar.SECOND, 0)
        endTime.set(Calendar.MILLISECOND, 0)

        // determine which day of week for today
        val timeNow = AppUtil.getCurrentTime()
        val dayOfWeek = timeNow.get(Calendar.DAY_OF_WEEK)
        val isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= Calendar.FRIDAY))
        if (isWeekday) {
            if (dayOfWeek == Calendar.FRIDAY && timeNow.timeInMillis > endTime.timeInMillis) {
                //if it is Friday and after market, calcuate add the weekend, 48 hours in MS
                nextAlarmInMS += (AlarmManager.INTERVAL_DAY * 2)
                //Log.d(TAG, "msToNextAlarm: Friday after 4:00PM  "  + nextAlarmInMS)
            }

            //  calculate the difference to s930AM
            if (timeNow.timeInMillis < startTime.timeInMillis) {
                // if before market time, calculate the difference to s930AM
                nextAlarmInMS += (startTime.timeInMillis - timeNow.timeInMillis + 15000L)
                //Log.d(TAG, "msToNextAlarm: before 9:30AM "  + nextAlarmInMS)
            } else if (timeNow.timeInMillis > endTime.timeInMillis) {
                //after market time, calculate the difference to mid-night and add 9 hours and 30 minutes
                val midNight = AppUtil.getMidNight()
                midNight.add(Calendar.DAY_OF_MONTH, 1)
                midNight.set(Calendar.HOUR_OF_DAY, 0)
                midNight.set(Calendar.MINUTE, 0)
                midNight.set(Calendar.SECOND, 0)
                midNight.set(Calendar.MILLISECOND, 0)
                //Log.d(TAG, "msToNextAlarm: = " + nextAlarmInMS)

                nextAlarmInMS += (midNight.timeInMillis - timeNow.timeInMillis)
                //Log.d(TAG, "msToNextAlarm: = " + nextAlarmInMS)

                nextAlarmInMS += (AlarmManager.INTERVAL_HOUR * 9 + AlarmManager.INTERVAL_HALF_HOUR + 15000L)
                //Log.d(TAG, "msToNextAlarm: after 4:00PM " + nextAlarmInMS + " 9hours = " + AlarmManager.INTERVAL_HOUR * 9)
                //Log.d(TAG, "msToNextAlarm: half_hour = " + AlarmManager.INTERVAL_HALF_HOUR)

            } else {
                // if is in market time, return 15 mins in MS
                nextAlarmInMS = AlarmManager.INTERVAL_FIFTEEN_MINUTES
                //Log.d(TAG, "msToNextAlarm: 15 minutes  " + nextAlarmInMS)
            }


        } else { // weekend,
            if (dayOfWeek == Calendar.SATURDAY) {
                //add 24 hours in MS for Sunday
                nextAlarmInMS += AlarmManager.INTERVAL_DAY
                //Log.d(TAG, "msToNextAlarm: +24 hours when it is Saturday " + nextAlarmInMS)
            }

            val midNight = AppUtil.getMidNight()
            midNight.add(Calendar.DAY_OF_MONTH, 1)
            midNight.set(Calendar.HOUR_OF_DAY, 0)
            midNight.set(Calendar.MINUTE, 0)
            midNight.set(Calendar.SECOND, 0)
            midNight.set(Calendar.MILLISECOND, 0)

            nextAlarmInMS += (midNight.timeInMillis - timeNow.timeInMillis)
            //Log.d(TAG, "msToNextAlarm: = " + nextAlarmInMS)

            nextAlarmInMS += (AlarmManager.INTERVAL_HOUR * 9 + AlarmManager.INTERVAL_HALF_HOUR + 15000L)
            //Log.d(TAG, "msToNextAlarm: after 4:00PM " + nextAlarmInMS)
        }


        //Log.d(TAG, "msToNextAlarm: = " + nextAlarmInMS + " for " + timeNow.time)
        return nextAlarmInMS
    }


    fun schedule(context: Context) {
        msToNextAlarm()

        val jobScheduler = context
            .getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val componentName = ComponentName(context, AlarmJobService::class.java)
        val builder = JobInfo.Builder(AlarmJobService.REQUEST_CODE, componentName)
        val nextAlarmInMs = msToNextAlarm()

        val pEditor = PreferenceManager
            .getDefaultSharedPreferences(context).edit()
        val calendar = AppUtil.getCurrentTime()
        calendar.timeInMillis += nextAlarmInMs
        pEditor.putString(PREF_NEXT_SCHEDULE_RUN, calendar.time.toString())
        pEditor.putBoolean(PREF_ALARM_STATUS, true)
        pEditor.apply()

        builder.setPersisted(true)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setMinimumLatency(nextAlarmInMs)
            .setOverrideDeadline(nextAlarmInMs + FIVE_MINUTES_MS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setRequiresBatteryNotLow(false)
                .setRequiresStorageNotLow(false)
        }

        val jobInfo = builder.build()
        val scheduled = jobScheduler.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS
        if (!scheduled) {
            Log.d("what", "onHandleIntent: Job schedule failed!")
        } else {
            Log.d("what", "onHandleIntent: Job schedule completed!")
        }
    }

    fun cancelSchedule(context: Context) {
        val jobScheduler = context
            .getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(AlarmJobService.REQUEST_CODE)

        //clear preference next run time
        val pedit = PreferenceManager.getDefaultSharedPreferences(context).edit()
        pedit.putBoolean(PREF_ALARM_STATUS, false)
        pedit.putString(PREF_NEXT_SCHEDULE_RUN, "Not scheduled")

        pedit.apply()
    }

    companion object {
        const val PREF_LAST_SCHEDULE_RuN = "last_run"
        const val PREF_NEXT_SCHEDULE_RUN = "next_run"
        const val PREF_ALARM_STATUS = "alarm_set"

        @Volatile
        private var INSTANCE: AlarmScheduler? = null

        fun getInstance(): AlarmScheduler =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AlarmScheduler().also { INSTANCE = it }
            }
    }

}