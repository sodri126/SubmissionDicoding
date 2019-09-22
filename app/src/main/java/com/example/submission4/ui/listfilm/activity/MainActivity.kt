package com.example.submission4.ui.listfilm.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.submission4.R
import com.example.submission4.base.BaseActivity
import com.example.submission4.data.api.model.DiscoverMovie
import com.example.submission4.data.api.model.DiscoverTv
import com.example.submission4.data.local.entity.MovieEntity
import com.example.submission4.data.local.entity.TvShowEntity
import com.example.submission4.ui.detailfilm.DetailActivity
import com.example.submission4.ui.favoritefilm.fragment.FavoriteFragment
import com.example.submission4.ui.listfilm.fragment.MainFragment
import com.example.submission4.ui.listfilm.navigator.IClickItem
import com.example.submission4.ui.listfilm.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class MainActivity : BaseActivity<MainViewModel>(), IClickItem {
    override val viewModel: MainViewModel by currentScope.viewModel(this)
    private lateinit var currentFragment: Fragment

    companion object {
        const val TAG_MOVIE = "TAGMOVIE"
        const val TAG_TV = "TAGTV"
        const val FRAGMENT_SAVED = "FRAGMENTSAVED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            bottom_navigation.selectedItemId = R.id.navigation_home
        } else {
            currentFragment = supportFragmentManager.getFragment(
                savedInstanceState,
                FRAGMENT_SAVED
            ) as Fragment
            loadFragment(currentFragment)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(
            outState,
            FRAGMENT_SAVED, currentFragment
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_language) {
            val intentLanguage = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intentLanguage)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setUpView() {
        setSupportActionBar(toolbar)
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    override fun getLayoutRestId(): Int = R.layout.activity_main

    override fun onError(e: String) {
        super.onError(e)
        val snackbar = Snackbar.make(main_container, e, Snackbar.LENGTH_LONG)
        snackbar.setAction(getString(R.string.general_ok)) {
            snackbar.dismiss()
        }.show()
    }

    override fun detailInformation(movie: Any) {
        val intentDetail = Intent(this, DetailActivity::class.java)
        when (movie) {
            is DiscoverMovie -> {
                intentDetail.putExtra("id", movie.id)
                intentDetail.putExtra("tag", TAG_MOVIE)
            }
            is DiscoverTv -> {
                intentDetail.putExtra("id", movie.id)
                intentDetail.putExtra("tag", TAG_TV)
            }
            is MovieEntity -> {
                intentDetail.putExtra("id", movie.movieId)
                intentDetail.putExtra("tag", TAG_MOVIE)
            }
            is TvShowEntity -> {
                intentDetail.putExtra("id", movie.tvShowId)
                intentDetail.putExtra("tag", TAG_TV)
            }
        }
        startActivity(intentDetail)
    }

    @SuppressLint("SimpleDateFormat")
    override fun favoriteMovie(movie: Any) {
        when (movie) {
            is DiscoverMovie -> {
                val sdf = SimpleDateFormat("MMMM dd, yyyy")
                val movieEntity = MovieEntity(
                    movie.id,
                    movie.title,
                    sdf.format(movie.releaseDate),
                    movie.overview,
                    movie.voteAverage,
                    movie.posterPath
                )
                viewModel.addFavoriteMovie(movieEntity)
                onError(getString(R.string.general_add_successfully))
            }
            is DiscoverTv -> {
                val sdf = SimpleDateFormat("MMMM dd, yyyy")
                val tvEntity = TvShowEntity(
                    movie.id,
                    movie.name,
                    sdf.format(movie.firstAirDate),
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
            is MovieEntity -> {
                viewModel.deleteMovie(movie.movieId)
                onError(getString(R.string.general_delete_succesffully))
            }
            is TvShowEntity -> {
                viewModel.deleteTvShow(movie.tvShowId)
                onError(getString(R.string.general_delete_succesffully))
            }
        }
    }

    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    toolbar.title = resources.getString(R.string.list_catalogue)
                    currentFragment = MainFragment()
                    loadFragment(currentFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    toolbar.title = resources.getString(R.string.list_favorite_catalogue)
                    currentFragment = FavoriteFragment()
                    loadFragment(currentFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_navigation_fragment, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Activity.RESULT_OK
    }
}
