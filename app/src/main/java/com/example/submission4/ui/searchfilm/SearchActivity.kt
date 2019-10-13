package com.example.submission4.ui.searchfilm

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.submission4.R
import com.example.submission4.base.BaseActivity
import com.example.submission4.data.api.model.DiscoverMovie
import com.example.submission4.data.api.model.DiscoverTv
import com.example.submission4.data.local.entity.MovieEntity
import com.example.submission4.data.local.entity.TvShowEntity
import com.example.submission4.ui.detailfilm.DetailActivity
import com.example.submission4.ui.listfilm.activity.MainActivity
import com.example.submission4.ui.listfilm.navigator.IClickItem
import com.example.submission4.ui.searchfilm.adapter.SearchAdapter
import com.example.submission4.ui.searchfilm.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.scope.currentScope
import java.util.*

class SearchActivity : BaseActivity<SearchViewModel>(), IClickItem {
    override val viewModel: SearchViewModel by currentScope.inject()
    override fun getLayoutRestId(): Int = R.layout.activity_search
    private val searchAdapter = SearchAdapter(this@SearchActivity, supportFragmentManager, 2)

    override fun setUpView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        search_view.apply {
            searchManager.getSearchableInfo(
                componentName
            )
            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextChange(text: String?): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        processSearch(it)
                    }
                    return false
                }
            })
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.apply{
            val submitSearchData = getBoolean("submitSearchData")
            if (submitSearchData) {
                container_fragment.adapter = searchAdapter
                tab_layout.setupWithViewPager(container_fragment)

                container_fragment.visibility = View.VISIBLE
                tab_layout.visibility = View.VISIBLE
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("submitSearchData", true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun detailInformation(movie: Any) {
        val intentDetail = Intent(this, DetailActivity::class.java)
        when (movie) {
            is DiscoverMovie -> {
                intentDetail.putExtra("id", movie.id)
                intentDetail.putExtra("tag", MainActivity.TAG_MOVIE)
            }
            is DiscoverTv -> {
                intentDetail.putExtra("id", movie.id)
                intentDetail.putExtra("tag", MainActivity.TAG_TV)
            }
        }
        startActivity(intentDetail)
    }

    override fun favoriteMovie(movie: Any) {
        when (movie) {
            is DiscoverMovie -> {
                val movieEntity = MovieEntity(
                    movie.id,
                    movie.title,
                    MainActivity.sdf.format(movie.releaseDate as Date),
                    movie.overview,
                    movie.voteAverage,
                    movie.posterPath
                )
                viewModel.addFavoriteMovie(movieEntity)
                onError(getString(R.string.general_add_successfully))
            }
            is DiscoverTv -> {
                val tvEntity = TvShowEntity(
                    movie.id,
                    movie.name,
                    MainActivity.sdf.format(movie.firstAirDate as Date),
                    movie.overview,
                    movie.voteAverage,
                    movie.posterPath
                )
                viewModel.addFavoriteTvShow(tvEntity)
                onError(getString(R.string.general_add_successfully))
            }
        }
    }

    override fun unFavoriteMovie(movie: Any) {
        when (movie) {
            is DiscoverMovie -> {
                viewModel.deleteMovie(movie.id)
                onError(getString(R.string.general_delete_succesffully))
            }
            is DiscoverTv -> {
                viewModel.deleteTvShow(movie.id)
                onError(getString(R.string.general_delete_succesffully))
            }
        }
    }
    override fun onError(e: String) {
        super.onError(e)
        val snackbar = Snackbar.make(main_container, e, Snackbar.LENGTH_LONG)
        snackbar.setAction(getString(R.string.general_ok)) {
            snackbar.dismiss()
        }.show()
    }

    private fun processSearch(query: String) {
        progress_bar.visibility = View.VISIBLE
        container_fragment.visibility = View.GONE
        tab_layout.visibility = View.GONE

        container_fragment.offscreenPageLimit = 2
        searchAdapter.query = query
        container_fragment.adapter = searchAdapter
        tab_layout.setupWithViewPager(container_fragment)

        container_fragment.visibility = View.VISIBLE
        tab_layout.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

}
