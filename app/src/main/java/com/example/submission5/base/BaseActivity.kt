package com.example.submission5.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {
    open val viewModel: VM? = null

    abstract fun getLayoutRestId(): Int

    abstract fun setUpView()
    open fun setUpObserver() {}
    open fun setUpEvents() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRestId())
        setUpEvents()
        setUpObserver()
        setUpView()
    }

    open fun onError(e: String) {}
}