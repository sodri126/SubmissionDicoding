package com.example.submission4.ui.listfilm.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission4.R
import com.example.submission4.base.BaseActivity
import com.example.submission4.base.BaseFragment
import com.example.submission4.data.api.model.DiscoverMovie
import com.example.submission4.data.api.model.DiscoverTv
import com.example.submission4.ui.listfilm.activity.MainActivity
import com.example.submission4.ui.listfilm.adapter.MovieAdapter
import com.example.submission4.ui.listfilm.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.*
import org.koin.androidx.scope.currentScope
import com.example.submission4.utils.Result
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : BaseFragment<TvShowViewModel>() {
    override val viewModel: TvShowViewModel by currentScope.viewModel(this)

    override fun getLayoutRestId(): Int = R.layout.fragment_tv_show

    companion object {
        // singleton
        @Volatile private var INSTANCE: TvShowFragment? = null
        fun getInstance(): TvShowFragment {
            return INSTANCE ?: synchronized(this){
                TvShowFragment().also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun setUpView() {
        resources.let {
            if (it.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                rv_tv_shows.layoutManager = LinearLayoutManager(this.context)
            } else {
                rv_tv_shows.layoutManager = GridLayoutManager(this.context, 2)
            }
        }
        rv_tv_shows.setHasFixedSize(true)
    }

    override fun setUpObserve() {
        super.setUpObserve()
        viewModel.listTvLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    is Result.Success -> {
//                        tvAdapter.movies.clear()
                        val adapter = MovieAdapter<DiscoverTv>(clickItem = this.activity as MainActivity)
                        adapter.movies = it.data.results
                        rv_tv_shows.adapter = adapter
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
