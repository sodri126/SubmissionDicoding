package com.example.submission5.ui.listfilm.fragment

import android.content.res.Configuration
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission5.R
import com.example.submission5.base.BaseActivity
import com.example.submission5.base.BaseFragment
import com.example.submission5.data.api.model.DiscoverMovie
import com.example.submission5.ui.listfilm.activity.MainActivity
import com.example.submission5.ui.listfilm.adapter.MovieAdapter
import com.example.submission5.ui.listfilm.viewmodel.MovieViewModel
import com.example.submission5.utils.Result
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment<MovieViewModel>() {
    override val viewModel: MovieViewModel by currentScope.viewModel(this)

    override fun getLayoutRestId(): Int = R.layout.fragment_movie

    override fun setUpView() {
        resources.let {
            if (it.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                rv_movies.layoutManager = LinearLayoutManager(this.context)
            } else {
                rv_movies.layoutManager = GridLayoutManager(this.context, 2)
            }
        }
        rv_movies.setHasFixedSize(true)
    }

    override fun setUpObserve() {
        super.setUpObserve()
        viewModel.listMovieLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        val adapter =
                            MovieAdapter<DiscoverMovie>(clickItem = this.activity as MainActivity)
                        adapter.movies = it.data.results
                        rv_movies.adapter = adapter
                        progress_bar.visibility = View.GONE
                    }
                    is Result.Error -> {
                        val baseActivity = activity as BaseActivity<*>
                        baseActivity.onError(it.message)
                        progress_bar.visibility = View.GONE
                    }
                    is Result.Loading -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}
