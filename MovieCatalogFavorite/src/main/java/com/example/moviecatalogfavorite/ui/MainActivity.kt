package com.example.moviecatalogfavorite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.moviecatalogfavorite.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var currentFragment: Fragment

    companion object {
        const val FRAGMENT_SAVED = "FRAGMENTSAVED"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

        if (savedInstanceState == null) {
            currentFragment = MovieFavoriteFragment()
            loadFragment(currentFragment)
        } else {
            currentFragment =
                supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_SAVED) as Fragment
            loadFragment(currentFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(
            outState,
            FRAGMENT_SAVED, currentFragment
        )
    }

    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_movie -> {
                    toolbar.title = resources.getString(R.string.activity_favorite_movie)
                    currentFragment = MovieFavoriteFragment()
                    loadFragment(currentFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_tv -> {
                    toolbar.title = resources.getString(R.string.activity_favorite_tv)
                    currentFragment = TvShowFavoriteFragment()
                    loadFragment(currentFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }


}
