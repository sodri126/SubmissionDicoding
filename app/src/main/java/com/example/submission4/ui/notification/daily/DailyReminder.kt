package com.example.submission4.ui.notification.daily

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.submission4.BuildConfig
import com.example.submission4.R
import com.example.submission4.ui.listfilm.activity.MainActivity
import java.util.*

class DailyReminder: BroadcastReceiver() {
    companion object {
        private const val REMINDER_ID = 50
        private const val CHANNEL_ID = "CHANNEL_OPEN_APPS"
        private const val CHANNEL_NAME = "Movie Catalog Channel Reminder Apps"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        sendNotification(context)
    }

    fun setDailyReminder(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intentDailyReminder = Intent(context, DailyReminder::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, BuildConfig.DAILY_OPEN_APPS_HOUR)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        if (System.currentTimeMillis() > calendar.timeInMillis) {
            calendar.add(Calendar.MINUTE, 1)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, REMINDER_ID, intentDailyReminder, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun cancelReminder(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intentDailyReminder = Intent(context, DailyReminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REMINDER_ID, intentDailyReminder, PendingIntent.FLAG_UPDATE_CURRENT)

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    private fun sendNotification(context: Context?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val ringtoneDefault = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mainIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_local_movies_white_24dp)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_local_movies_white_24dp))
            .setContentTitle(context.resources.getString(R.string.general_setting_notification_title))
            .setContentText(context.resources.getString(R.string.general_setting_notification_text))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(ringtoneDefault)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(REMINDER_ID, notification)
    }

}