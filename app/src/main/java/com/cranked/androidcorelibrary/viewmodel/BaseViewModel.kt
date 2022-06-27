package com.cranked.androidcorelibrary.viewmodel

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

class BaseViewModel : ViewModel() {
    private var viewDataBinding: ViewDataBinding? = null
    fun setViewDataBinding(viewDataBinding: ViewDataBinding) {
        this.viewDataBinding = viewDataBinding
    }

    override fun onCleared() {
        super.onCleared()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }
}