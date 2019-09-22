package com.example.submission4.ui.favoritefilm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.submission4.R
import com.example.submission4.base.BaseFragment
import com.example.submission4.ui.favoritefilm.adapter.FavoriteViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : BaseFragment<Nothing>() {
    companion object {
        // singleton
        @Volatile private var INSTANCE: FavoriteFragment? = null
        fun getInstance(): FavoriteFragment {
            return INSTANCE
                ?: synchronized(this){
                FavoriteFragment().also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun getLayoutRestId(): Int = R.layout.fragment_favorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun setUpView() {
//        container_fragment.offscreenPageLimit = 3
//        container_fragment.adapter = FavoriteViewPagerAdapter(requireContext(), childFragmentManager, 2)
//        tab_layout.setupWithViewPager(container_fragment)
    }

}
