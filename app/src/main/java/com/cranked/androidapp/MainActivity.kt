package com.cranked.androidapp

import android.os.Bundle
import androidx.lifecycle.Observer
import com.cranked.androidcorelibrary.R
import com.cranked.androidcorelibrary.databinding.ActivityMainBinding
import com.cranked.androidcorelibrary.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java,
    R.layout.activity_main
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    override fun initViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    fun init() {
        binding.viewModel!!.stringText.observe(this
        ) { t ->
            binding.textView.text = t?.let {
                t
            }
        }
    }
}