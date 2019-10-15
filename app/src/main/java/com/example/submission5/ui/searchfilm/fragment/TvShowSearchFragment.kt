package com.example.submission5.ui.searchfilm.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission5.R
import com.example.submission5.base.BaseActivity
import com.example.submission5.base.BaseFragment
import com.example.submission5.data.api.model.DiscoverTv
import com.example.submission5.ui.listfilm.adapter.MovieAdapter
import com.example.submission5.ui.searchfilm.SearchActivity
import com.example.submission5.ui.searchfilm.viewmodel.TvShowSearchViewModel
import com.example.submission5.utils.Result
import kotlinx.android.synthetic.main.fragment_tv_show.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowSearchFragment : BaseFragment<TvShowSearchViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance(query: String) = TvShowSearchFragment().apply {
            arguments = Bundle().apply {
                putString("query", query)
            }
        }
    }
    override val viewModel: TvShowSearchViewModel by currentScope.viewModel(this)

    override fun getLayoutRestId(): Int = R.layout.fragment_tv_show

    override fun setUpView() {
        resources.let {
            if (it.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                rv_tv_shows.layoutManager = LinearLayoutManager(this.context)
            } else {
                rv_tv_shows.layoutManager = GridLayoutManager(this.context, 2)
            }
        }
        rv_tv_shows.setHasFixedSize(true)

        val query = arguments?.getString("query") as String
        viewModel.fetchSearchTvShow(query)
    }



    override fun setUpObserve() {
        super.setUpObserve()
        viewModel.getLiveDataListTvShow().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        val adapter =
                            MovieAdapter<DiscoverTv>(clickItem = this.activity as SearchActivity)
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