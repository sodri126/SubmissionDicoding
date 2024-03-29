package com.example.submission4.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.submission4.R

fun ImageView.glideLoadImage(
    url: String,
    width: Int = 760,
    height: Int = 400,
    progressBar: ProgressBar
) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.no_image_available)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }

        })
        .apply(RequestOptions.overrideOf(width, height))
        .error(R.drawable.no_image_available)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

suspend fun <T : Any> apiCall(call: suspend () -> Result<T>): Result<T> = try {
    call.invoke()
} catch (e: Exception) {
    Result.Error(e.message as String)
}