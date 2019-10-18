package com.example.submission5.ui.notification.releasetoday

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.submission5.BuildConfig
import com.example.submission5.R
import com.example.submission5.data.api.model.DiscoverMovie
import com.example.submission5.data.repository.MovieRepository
import com.example.submission5.ui.detailfilm.DetailActivity
import com.example.submission5.ui.listfilm.activity.MainActivity
import com.example.submission5.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class ReleaseTodayReminder : BroadcastReceiver(), KoinComponent {
    private val movieRepository: MovieRepository by inject()
    private val listTitle = mutableListOf<String>()

    companion object {
        private const val REMINDER_RELEASE_TODAY_ID = 51
        private var NOTIFICATION_ID = 0
        private const val CHANNEL_ID = "CHANNEL_RELEASE_TODAY"
        private const val CHANNEL_NAME = "Movie Catalog Channel Reminder Today"
        private const val MAX_NOTIFICATION = 5
        private const val GROUP_KEY_RELEASE = "GROUP_KEY_RELEASE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        listTitle.clear()
        CoroutineScope(Dispatchers.Main).launch {
            val listMovies = withContext(Dispatchers.IO) {
                return@withContext movieRepository.getListMoviesToday()
            }
            if (listMovies is Result.Success) {
                val detailIntent = Intent(context, DetailActivity::class.java)
                for (movie in listMovies.data.results) {
                    listTitle.add(movie.title)
                    showNotificationReleaseToday(context, NOTIFICATION_ID, movie, detailIntent)
                    NOTIFICATION_ID++
                }
            }
            NOTIFICATION_ID = 1
        }
    }

    private fun showNotificationReleaseToday(
        context: Context?,
        notificationId: Int,
        movie: DiscoverMovie,
        detailIntent: Intent
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val ringtoneDefault = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            detailIntent.putExtra("id", movie.id)
            detailIntent.putExtra("tag", MainActivity.TAG_MOVIE)

            val builder: NotificationCompat.Builder
            val pendingIntent = PendingIntent.getActivity(
                context,
                notificationId,
                detailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val imageBitmap = withContext(Dispatchers.IO) {
                return@withContext Glide.with(context)
                    .asBitmap()
                    .load("${BuildConfig.PATH_IMG}/w500${movie.posterPath}")
                    .submit(48, 48)
                    .get()
            }

            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_local_movies_white_24dp)
                .setContentTitle(movie.title)
                .setLargeIcon(imageBitmap)
                .setGroup(GROUP_KEY_RELEASE)
                .setContentText(
                    String.format(
                        context.resources.getString(R.string.general_setting_content_text),
                        movie.title
                    )
                )
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(ringtoneDefault)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

                builder.setChannelId(CHANNEL_ID)
                notificationManager.createNotificationChannel(channel)
            }

            val notification = builder.build()
            notificationManager.notify(notificationId, notification)
        }
    }

    fun cancelReleaseReminderToday(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intentReleaseTodayReminder = Intent(context, ReleaseTodayReminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REMINDER_RELEASE_TODAY_ID, intentReleaseTodayReminder, PendingIntent.FLAG_UPDATE_CURRENT
        )

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    fun setReleaseReminderToday(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intentReleaseService = Intent(context, ReleaseTodayReminder::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, BuildConfig.RELEASE_MOVIE_HOUR)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        if (System.currentTimeMillis() > calendar.timeInMillis) {
            calendar.add(Calendar.DATE, 1)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REMINDER_RELEASE_TODAY_ID,
            intentReleaseService,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

}