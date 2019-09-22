package com.example.submission4.ui.listfilm.adapter

import android.content.Context
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.submission4.R
import com.example.submission4.ui.listfilm.fragment.MovieFragment
import com.example.submission4.ui.listfilm.fragment.TvShowFragment
import java.lang.Exception

class MainViewPagerAdapter(private val context: Context, supportFragmentManager: FragmentManager, private val tabCount: Int): FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment.getInstance()
            1 -> TvShowFragment.getInstance()
            else -> MovieFragment.getInstance()
        }
    }

    override fun getCount(): Int = tabCount

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> this.context.getString(R.string.tab_movies)
            1 -> this.context.getString(R.string.tab_tv_shows)
            else -> this.context.getString(R.string.tab_movies)
        }
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try {
            super.restoreState(state, loader)
        } catch (ex: Exception) {

        }
    }
}