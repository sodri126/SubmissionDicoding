package com.example.submission5.ui.listfilm.fragment


import androidx.fragment.app.Fragment
import com.example.submission5.R
import com.example.submission5.base.BaseFragment
import com.example.submission5.ui.listfilm.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment<Nothing>() {

    override fun getLayoutRestId(): Int = R.layout.fragment_main

    override fun setUpView() {
        container_fragment.offscreenPageLimit = 2
        container_fragment.adapter = MainViewPagerAdapter(requireContext(), childFragmentManager, 2)
        tab_layout.setupWithViewPager(container_fragment)
    }
}
