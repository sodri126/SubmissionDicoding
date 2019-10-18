package com.example.submission5.ui.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.submission5.BuildConfig
import com.example.submission5.R
import com.example.submission5.data.local.FilmRoomDatabase
import com.example.submission5.data.local.widget.WidgetItem
import com.example.submission5.ui.listfilm.activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

internal class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {
    private val widgetItems = mutableListOf<WidgetItem>()
    private val database: FilmRoomDatabase by lazy { FilmRoomDatabase.getDatabase(context) }

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0L

    override fun onDataSetChanged() {
        val items = mutableListOf<WidgetItem>()
        val allMovies = runBlocking(Dispatchers.IO) {
            database.movieDao().getAllMovies()
        }

        val allTvShows = runBlocking(Dispatchers.IO) {
            database.tvShowDao().getAllTvs()
        }

        for (movie in allMovies) {
            items.add(
                WidgetItem(
                    movie.movieId,
                    movie.movieImagePath,
                    movie.movieTitle,
                    MainActivity.TAG_MOVIE
                )
            )
        }

        for (tv in allTvShows) {
            items.add(
                WidgetItem(
                    tv.tvShowId,
                    tv.tvShowImagePath,
                    tv.tvShowTitle,
                    MainActivity.TAG_TV
                )
            )
        }
        val identityToken = Binder.clearCallingIdentity()
        widgetItems.clear()
        widgetItems.addAll(items)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)

        val imageBitmap = Glide.with(context)
            .asBitmap()
            .load("${BuildConfig.PATH_IMG}/w780${widgetItems[position].imagePath}")
            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()

        rv.setImageViewBitmap(R.id.imageView, imageBitmap)
        if (widgetItems[position].typeWidget == MainActivity.TAG_MOVIE) rv.setTextViewText(
            R.id.txt_category,
            context.resources.getString(R.string.tab_movies)
        )
        else rv.setTextViewText(
            R.id.txt_category,
            context.resources.getString(R.string.tab_tv_shows)
        )

        rv.setTextViewText(R.id.txt_title, widgetItems[position].title)

        val bundle = bundleOf(
            MovieBannerWidget.ID to widgetItems[position].id,
            MovieBannerWidget.TAG to widgetItems[position].typeWidget
        )
        val intentDetailMovie = Intent()
        intentDetailMovie.putExtras(bundle)
        rv.setOnClickFillInIntent(R.id.imageView, intentDetailMovie)
        return rv
    }

    override fun getCount(): Int = widgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }
}