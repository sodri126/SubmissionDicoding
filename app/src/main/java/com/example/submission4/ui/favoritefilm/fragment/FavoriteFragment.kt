package com.example.submission4.ui.favoritefilm.fragment

import androidx.fragment.app.Fragment
import com.example.submission4.R
import com.example.submission4.base.BaseFragment
import com.example.submission4.ui.favoritefilm.adapter.FavoriteViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */

class FavoriteFragment : BaseFragment<Nothing>() {
    override fun getLayoutRestId(): Int = R.layout.fragment_favorite

    override fun setUpView() {
        container_fragment.offscreenPageLimit = 2
        container_fragment.adapter =
            FavoriteViewPagerAdapter(requireContext(), childFragmentManager, 2)
        tab_layout.setupWithViewPager(container_fragment)
    }


}