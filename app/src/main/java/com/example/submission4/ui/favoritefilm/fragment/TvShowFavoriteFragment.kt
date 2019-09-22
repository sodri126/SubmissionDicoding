package com.example.submission4.ui.favoritefilm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.submission4.R

class TvShowFavoriteFragment : Fragment() {

    companion object {
        // singleton
        @Volatile private var INSTANCE: TvShowFavoriteFragment? = null
        fun getInstance(): TvShowFavoriteFragment {
            return INSTANCE
                ?: synchronized(this){
                    TvShowFavoriteFragment().also {
                        INSTANCE = it
                    }
                }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }


}
