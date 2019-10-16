package com.example.moviecatalogfavorite.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogfavorite.R
import com.example.moviecatalogfavorite.data.model.FavoriteMovie
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private val  mainViewModel: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val mainAdapter by lazy { MainAdapter<FavoriteMovie>() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setUpView()
        fetchDataAndLiveData()
    }

    private fun setUpView() {
        resources.let {
            if (it.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                rv_movies.layoutManager = LinearLayoutManager(this)
            } else {
                rv_movies.layoutManager = GridLayoutManager(this, 2)
            }
        }
        rv_movies.setHasFixedSize(true)
    }

    private fun fetchDataAndLiveData() {
        mainViewModel.getListMovieFavorite()

        mainViewModel.favoriteLiveData().observe(this, Observer {
            it?.apply {
                mainAdapter.movies = this as ArrayList<FavoriteMovie>
                rv_movies.adapter = mainAdapter
            }
        })

        mainViewModel.isLoading().observe(this, Observer {
            if (it == false) progress_bar.visibility = View.GONE
            else progress_bar.visibility = View.VISIBLE
        })
    }
}
