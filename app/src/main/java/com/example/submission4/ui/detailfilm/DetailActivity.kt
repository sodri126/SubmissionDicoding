package com.example.submission4.ui.detailfilm

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.example.submission4.BuildConfig
import com.example.submission4.R
import com.example.submission4.base.BaseActivity
import com.example.submission4.data.api.model.DetailMovie
import com.example.submission4.data.api.model.DetailTv
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.toolbar
import org.koin.androidx.scope.currentScope
import com.example.submission4.utils.Result
import com.example.submission4.utils.glideLoadImage
import kotlinx.android.synthetic.main.activity_detail.txt_rating
import kotlinx.android.synthetic.main.activity_detail.txt_release_date
import kotlinx.android.synthetic.main.activity_detail.txt_title_movie
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat

class DetailActivity : BaseActivity<DetailViewModel>() {
    override val viewModel: DetailViewModel by currentScope.viewModel(this) {
        parametersOf(intent?.extras?.get("id"), intent?.extras?.get("tag"))
    }
    private var homepage: String = ""

    override fun setUpView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = ""
    }

    override fun getLayoutRestId(): Int = R.layout.activity_detail

    override fun setUpObserver() {
        super.setUpObserver()
        viewModel.fetchData().observe(this, Observer {
            it?.run {
                when(it) {
                    is Result.Loading -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    is Result.Success<Any> -> {
                        setUpViewToData(it.data)
                        progress_bar.visibility = View.GONE
                    }
                    is Result.Error -> {
                        progress_bar.visibility = View.GONE
                        onError(it.message)
                    }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setUpViewToData(model: Any) {
        val sdf = SimpleDateFormat("MMMM dd, yyyy")
        when (model) {
            is DetailMovie -> {
                img_back_drop.glideLoadImage("${BuildConfig.PATH_IMG}/w780${model.backdropPath}", 780, 500, progress_bar_img_backdrop)
                img_poster.glideLoadImage("${BuildConfig.PATH_IMG}/w780${model.posterPath}", 780, 500, progress_bar_poster_img)
                supportActionBar?.title = model.title
                txt_title_movie.text = model.title
                txt_rating.text = model.voteAverage.toString()
                txt_duration.text = "${model.runTime/60}${resources.getString(R.string.detail_label_runtime_hour)} ${model.runTime%60}${resources.getString(R.string.detail_label_runtime_minute)}"
                txt_release_date.text = sdf.format(model.releaseDate)
                txt_language.text = if (model.originalLanguage == "en") resources.getString(R.string.general_en) else model.originalLanguage
                txt_status.text = model.status
                txt_genres.text = model.genres.joinToString { it.genreName }
                txt_overview.text = model.overview
                homepage = model.homepage
                if (model.isFavorite) fab_favorite.setImageResource(R.drawable.ic_favorite_white_24dp)
                else fab_favorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
            }
            is DetailTv -> {
                val runTime = if (model.episodeRunTime.isNotEmpty()) model.episodeRunTime[0] else 0
                img_back_drop.glideLoadImage("${BuildConfig.PATH_IMG}/w780${model.backdropPath}", 780, 500, progress_bar_img_backdrop)
                img_poster.glideLoadImage("${BuildConfig.PATH_IMG}/w780${model.posterPath}", 780, 500, progress_bar_poster_img)
                supportActionBar?.title = model.name
                txt_title_movie.text = model.name
                txt_rating.text = model.voteAverage.toString()
                txt_duration.text = "${runTime/60}${resources.getString(R.string.detail_label_runtime_hour)} ${runTime%60}${resources.getString(R.string.detail_label_runtime_minute)}"
                txt_release_date.text = sdf.format(model.firstAirDate)
                txt_language.text = if (model.originalLanguage == "en") resources.getString(R.string.general_en) else model.originalLanguage
                txt_status.text = model.status
                txt_genres.text = model.genres.joinToString { it.genreName }
                txt_overview.text = model.overview
                homepage = model.homePage
                if (model.isFavorite) fab_favorite.setImageResource(R.drawable.ic_favorite_white_24dp)
                else fab_favorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
            }
        }
    }

    override fun onError(e: String) {
        super.onError(e)
        Snackbar.make(detail_container, e, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home ->  {
                onBackPressed()
                return true
            }
            R.id.homepage -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(homepage))
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
