package com.mcarving.stocktracker.Schedules

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.preference.PreferenceManager
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.portfolios.PortfoliosActivity
import com.mcarving.stocktracker.stocks.StocksActivity
import java.util.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class AlarmJobService : JobService() {


    private lateinit var mNotificationManager: NotificationManager

    override fun onStopJob(p0: JobParameters?): Boolean {

        //return true if job fails, you want the job to be rescheduled instead of dropped
        return true
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        val pEditor = PreferenceManager
            .getDefaultSharedPreferences(applicationContext).edit()
        val calendar = AppUtil.getCurrentTime()
        pEditor.putString(AlarmScheduler.PREF_LAST_SCHEDULE_RuN, calendar.time.toString())
        pEditor.apply()

        mNotificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()

        doMyWork()
        AlarmScheduler.getInstance().schedule(applicationContext)
        return true
    }

    private fun doMyWork() {
        deliverNotification()
    }


    private fun deliverNotification() {

        val contentIntent = Intent(applicationContext, PortfoliosActivity::class.java)

        val contentPendingInt = PendingIntent.getActivity(
            applicationContext,
            REQUEST_CODE,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle("Test Title")
            .setContentText("Test Content Text")
            .setContentIntent(contentPendingInt)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        // Deliver the notification
        mNotificationManager.notify(REQUEST_CODE, builder.build())

    }


    fun createNotificationChannel() {

        // Notification channels are only available in OREO and higher.
        // so, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Test notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifies every 15 minutes to fetch market data"
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

    }

    companion object {
        const val REQUEST_CODE = 0
        const val FIFTEEN_SEC_MILLIS : Long = 15000
        const val SIXTY_SEC_MILLIS : Long = 60000
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"

    }

}