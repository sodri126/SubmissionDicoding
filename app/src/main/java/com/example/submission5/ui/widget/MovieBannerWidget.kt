package com.example.submission5.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.example.submission5.R
import com.example.submission5.ui.detailfilm.DetailActivity

/**
 * Implementation of App Widget functionality.
 */
class MovieBannerWidget : AppWidgetProvider() {
    companion object {
        private const val ON_CLICK_WIDGET = "OnClickWidget"
        const val ID = "id"
        const val TAG = "tag"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.movie_banner_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.txt_title)

            val detailIntent = Intent(context, MovieBannerWidget::class.java)
            detailIntent.action = ON_CLICK_WIDGET
            detailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val detailPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                detailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stack_view, detailPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.let {
            if (it.action == ON_CLICK_WIDGET) {
                val id = intent.getIntExtra(ID, 0)
                val tag = intent.getStringExtra(TAG)

                val intentDetailMovie = Intent(context, DetailActivity::class.java)
                intentDetailMovie.putExtra(ID, id)
                intentDetailMovie.putExtra(TAG, tag)
                intentDetailMovie.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(intentDetailMovie)
            }
        }
    }
}

