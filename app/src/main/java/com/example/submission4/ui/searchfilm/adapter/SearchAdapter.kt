package com.example.submission4.ui.searchfilm.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.submission4.R
import com.example.submission4.ui.searchfilm.fragment.MovieSearchFragment
import com.example.submission4.ui.searchfilm.fragment.TvShowSearchFragment

class SearchAdapter(
    private val context: Context,
    supportFragmentManager: FragmentManager,
    private val tabCount: Int
) : FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var query: String = ""
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieSearchFragment.newInstance(query)
            1 -> TvShowSearchFragment.newInstance(query)
            else -> MovieSearchFragment.newInstance(query)
        }
    }

    override fun getCount(): Int = tabCount

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> this.context.getString(R.string.tab_movies)
            1 -> this.context.getString(R.string.tab_tv_shows)
            else -> this.context.getString(R.string.tab_movies)
        }
    }
}