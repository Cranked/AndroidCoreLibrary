package com.cranked.androidcorelibrary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cranked.androidcorelibrary.viewmodel.BaseViewModel

class BaseActivity<VM : BaseViewModel>: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}