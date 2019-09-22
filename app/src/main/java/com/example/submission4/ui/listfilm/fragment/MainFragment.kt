package com.example.submission4.ui.listfilm.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.submission4.R
import com.example.submission4.base.BaseFragment
import com.example.submission4.ui.listfilm.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment<Nothing>() {
    private lateinit var adapter: MainViewPagerAdapter

    companion object {
        // singleton
        @Volatile private var INSTANCE: MainFragment? = null
        fun getInstance(): MainFragment {
            return INSTANCE ?: synchronized(this){
                MainFragment().also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.adapter = MainViewPagerAdapter(requireContext(), childFragmentManager, 2)
    }

    override fun getLayoutRestId(): Int = R.layout.fragment_main

    override fun setUpView() {
        container_fragment.offscreenPageLimit = 3
        container_fragment.adapter = adapter
        tab_layout.setupWithViewPager(container_fragment)
    }
}
