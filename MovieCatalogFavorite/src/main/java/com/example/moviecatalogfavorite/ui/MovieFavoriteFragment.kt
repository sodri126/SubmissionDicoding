package com.example.moviecatalogfavorite.ui


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogfavorite.R
import com.example.moviecatalogfavorite.data.model.FavoriteMovie
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFavoriteFragment : Fragment() {
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    private val mainAdapter by lazy { MainAdapter<FavoriteMovie>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        fetchDataAndLiveData()
    }

    private fun setUpView() {
        resources.let {
            if (it.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                rv_movies.layoutManager = LinearLayoutManager(requireContext())
            } else {
                rv_movies.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
        rv_movies.setHasFixedSize(true)
    }

    private fun fetchDataAndLiveData() {
        mainViewModel.getListMovieFavorite()

        mainViewModel.favoriteMovieLiveData().observe(this, Observer {
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
